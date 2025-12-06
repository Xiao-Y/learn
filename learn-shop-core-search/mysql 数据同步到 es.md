# mysql 数据同步到 es

## !!!前提：mysql 开启 binlog，在 MQ 中新建交换机 canal_exchange，绑定需要接收消息的队列 canal_queue 

### canal 接到 binlog 中的变化的数据后，通过配置的 MQ 发送消息到对应的交换机配置：

```properties
##################################################
#########                   RabbitMQ         #############
##################################################
rabbitmq.host = rabbitmq
rabbitmq.virtual.host = /learn-default
rabbitmq.exchange = canal_exchange
rabbitmq.username = admin
rabbitmq.password = admin123
# rabbitmq.deliveryMode = admin123

```

### 在代码中接收消息处理数据
```
    /**
     * 同步 mysql 数据到 es
     *
     * @param message
     * @author liuyongtao
     * @since 2021-9-1 15:34
     */
    @Async("fxbDrawExecutor")
    @RabbitListener(queues = "canal_queue")
    @RabbitHandler
    public void syncEs(String message) {
        log.info("mysql：{}", message);
        CanalDbVo canalDbVo = JSON.parseObject(message, CanalDbVo.class);
        if (!"update".equalsIgnoreCase(canalDbVo.getType())
                && !"INSERT".equalsIgnoreCase(canalDbVo.getType())
                && !"DELETE".equalsIgnoreCase(canalDbVo.getType())) {
            return;
        }
        // 获取表名
        String table = canalDbVo.getTable();
        // 更新前的字段和值
        List<String> old = canalDbVo.getOld();
        // 更新后的所有字段和值
        List<String> data = canalDbVo.getData();
        log.info("有变化的表:{}", table);
        // 更新商品
        if (DbTableNameConstant.TABLE_PMS_GOODS_SPU.equalsIgnoreCase(table)) {
            this.upateGoodsInfo(data, canalDbVo.getType());
        } else if (DbTableNameConstant.TABLE_PMS_GOODS_CATEGORY.equalsIgnoreCase(table)) {// 更新分类
            updateGoodsInfoPartCategoryName(old, data);
        } else if (DbTableNameConstant.TABLE_PMS_GOODS_BRAND.equalsIgnoreCase(table)) {// 更新品牌
            updateGoodsInfoPartBrandName(old, data);
        }
        log.info("完成刷新...");
    }
```


## ES索引的keyword类型和text类型以及termQuery,match,match_phrase区别



| 关键词       | keyword类型 | text类型                                                     | 是否支持分词 | 对应的方法  |
| ------------ | ----------- | ------------------------------------------------------------ | ------------ | ----------- |
| term         | 完全匹配    | 查询条件必须都是text分词中的，且不能多余，多个分词时必须连续，顺序不能颠倒 | 否           | eq          |
| match        | 完全匹配    | mahch分词结果和text的分词结果有相同的就行，不考虑顺序        | 是           | match       |
| match_phrase | 完全匹配    | match_phrase的分词结果必须在text字段分词中都包含，而且顺序必须相同，而且必须都是连续的 | 是           | matchPhrase |
| query_string | 完全匹配    | query_string中的分词结果至少有一个在text字段的分词结果中，不考虑顺序 | 是           |             |
|              |             |                                                              |              |             |

easy-es
https://www.easy-es.cn/pages/7ead0d/#%E7%AE%80%E4%BB%8B