<template>
  <div>
    <el-row>
      <el-col :span="6" v-for="(o, index) in moduleList" :key="o">
        <el-card :body-style="{ padding: '0px 0px 5px 0px' }" style="margin: 0 7px 14px 14px">
          <div class="card_main_title">
            {{o.title}}
          </div>
          <div class="card_main_content">
            <div>
              系统全称：{{o.content}}
            </div>
            <div>
              版本号：{{o.version}}
            </div>
            <div>
              最近更新时间：{{o.lastUpdatetime}}
            </div>
          </div>
          <div class="card_main_button">
            <div class="bottom clearfix">
              <el-button type="text" class="button">操作按钮</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentDate: new Date(),
      // 数据
      moduleList: [{
        title: 'cloud-gateway',
        content: '认证网关',
        version: '1.0.0',
        lastUpdatetime: '2025-10-01'
      },{
        title: 'admin-system',
        content: '系统管理',
        version: '1.0.0',
        lastUpdatetime: '2025-10-01'
      },
        {
          title: 'core-cart',
          content: '购物车管理',
          version: '1.0.0',
          lastUpdatetime: '2025-10-01'
        },
        {
          title: 'core-file',
          content: '文件管理',
          version: '1.0.0',
          lastUpdatetime: '2025-10-01'
        },
        {
          title: 'core-order',
          content: '订单管理',
          version: '1.0.0',
          lastUpdatetime: '2025-10-01'
        },
        {
          title: 'core-product',
          content: '商品管理',
          version: '1.0.0',
          lastUpdatetime: '2025-10-01'
        },
        {
          title: 'core-promotion',
          content: '营销管理',
          version: '1.0.0',
          lastUpdatetime: '2025-10-01'
        },
        {
          title: 'core-search',
          content: '搜索管理',
          version: '1.0.0',
          lastUpdatetime: '2025-10-01'
        }]
    };
  }
}
</script>

<style>
.card_main_title {
  color: white;
  background-color: #1890ff;
  font-size: 30px;
  line-height: 1.5;
  padding-left: 14px;
}

.card_main_content {
  padding: 14px 0 0 14px;
  font-size: 14px;
  line-height: 1.5;
}

.card_main_button {
  padding: 14px;
}

.bottom {
  margin-top: 10px;
  line-height: 1.5;
}

.button {
  padding: 0;
  float: right;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>
