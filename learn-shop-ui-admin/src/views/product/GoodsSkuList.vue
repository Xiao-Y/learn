<template>
  <div>
    <el-row>
      <el-table :data="tableData" border style="width:100%">
        <el-table-column label="SKU编号" prop="skuNo" width="200"></el-table-column>
        <el-table-column label="SKU名称" prop="skuName"></el-table-column>
        <el-table-column label="说明" prop="specKeyValueName" width="200"></el-table-column>
        <el-table-column label="售价" prop="price"></el-table-column>
        <el-table-column label="库存量" prop="stock"></el-table-column>
        <el-table-column label="是否有效" prop="stock" width="80">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.validInd ? 'success' : 'danger'"
              disable-transitions>{{scope.row.validInd | validIndName}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200" v-if="showOption">
          <template slot-scope="scope">
            <!--  操作按钮组 -->
            <button-group-option @onDel="handleDelete(scope.row,scope.$index)"
                                 @onEdit="handleEdit(scope.row,scope.$index)"
                                 @onInd="handleProhibit(scope.row,scope.$index)"
                                 :disInd="!scope.row.validInd"></button-group-option>
          </template>
        </el-table-column>
        <el-table-column type="expand" label="详细" width="50">
          <template slot-scope="scope">
            <el-form label-position="left" inline class="demo-table-expand" label-width="120px">
              <el-form-item label="创建人">
                <span>{{ scope.row.creatorCode }}</span>
              </el-form-item>
              <el-form-item label="更新人">
                <span>{{ scope.row.updaterCode }}</span>
              </el-form-item>
              <el-form-item label="创建时间">
                <el-date-picker type="datetime" v-model="scope.row.createTime" readonly></el-date-picker>
              </el-form-item>
              <el-form-item label="更新时间">
                <el-date-picker type="datetime" v-model="scope.row.updateTime" readonly></el-date-picker>
              </el-form-item>
              <el-form-item label="是否有效">
                <el-switch v-model="scope.row.validInd" active-text="有效" inactive-text="无效" disabled></el-switch>
              </el-form-item>
              <el-form-item label="商铺ID">
                <span>{{ scope.row.shopId }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
      </el-table>
    </el-row>

    <el-dialog :title="tableTitle" :visible.sync="showSkuEdit" v-if="showSkuEdit">
      <goods-sku-edit :sku-id="skuId" :spu-id="spuId" :category-id="categoryId" :spec-key-value="specKeyValue"
                      @closeDialog="closeDialog" v-if="showSkuEdit"/>
    </el-dialog>
  </div>
</template>


<script>
  import {FindGoodsSku, DelById, ProhibitById} from "../../api/product/GoodsSkuApi";
  // ===== 工具类 start
  import VueUtils from "../../utils/vueUtils";
  import pageMixins from "../../utils/pageMixins";

  // ===== component start
  import GoodsSkuEdit from './components/GoodsSkuEdit.vue';
  import ButtonGroupOption from '../../components/common/ButtonGroupOption.vue';

  export default {
    components: {
      GoodsSkuEdit,
      ButtonGroupOption
    },
    props: {
      spuId: {
        type: String,
        default: null
      },
      showOption: {
        type: Boolean,
        default: false
      },
      categoryId: {
        type: String,
        default: null
      },
      specKeys: {
        default: null
      }
    },
    mixins: [pageMixins],
    data() {
      return {
        showSkuEdit: false,// 打开SKU窗口
        tableTitle: '',// SKU name
        skuId: null,// 商品ID
        specKeyValue: null,// 规格key-value
        tableData: []
      }
    },
    created() {
      // 请数据殂
      this.loadDataList();
    },
    methods: {
      // 获取SKU列表数据
      loadDataList() {
        if (this.spuId === null) {
          return;
        }
        FindGoodsSku(this.spuId).then(res => {
          this.tableData = res.resData;
        });
      },
      // 添加SKU
      handleAdd() {
        this.showSkuEdit = true;
        this.tableTitle = '添加SKU';
        this.skuId = null;
        this.specKeyValue = null;
      },
      handleEdit(row, index) {
        this.showSkuEdit = true;
        this.tableTitle = '修改SKU';
        this.skuId = row.id;
        this.specKeyValue = [];
        if (this.specKeys) {
          for (let i in this.specKeys) {
            var value = row.goodsSkuSpecValueVos.find(f => f.specKeyId === this.specKeys[i]);
            if (value) {
              this.specKeyValue.push(value);
            } else {
              this.specKeyValue.push({specKeyId: this.specKeys[i], specValueId: null});
            }
          }
        }
        console.info('this.specKeyValue', this.specKeyValue);
      },
      handleDelete(row, index) {
        var _this = this;

        VueUtils.confirmDel(row.skuName, () => {
          DelById(row.id).then(res => {
            _this.tableData.splice(index, 1);
            _this.$message({
              type: 'success',
              message: '删除成功!'
            });
          });
        });
      },
      handleProhibit(row, index) {
        var _this = this;

        VueUtils.confirmInd(row.skuName, () => {
          ProhibitById(row.id).then(res => {
            row.validInd = res.resData.validInd;
            _this.$message({
              type: 'success',
              message: '禁用成功!'
            });
          });
        });
      },
      closeDialog(type) {
        this.showSkuEdit = false;
        if (type === 'refresh') {
          this.loadDataList();
        }
      }

    },
    filters: {
      validIndName(validInd) {
        return validInd ? '有效' : '无效';
      }
    }
  }
</script>

<style scoped>
  .el-row {
    margin-bottom: 5px;
  }

  .el-form-item {
    margin-bottom: 3px;
  }

  /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
  ::-webkit-scrollbar {
    width: 3px; /*滚动条宽度*/
    height: 3px; /*滚动条高度*/
  }

  /*定义滚动条轨道 内阴影+圆角*/
  ::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    border-radius: 10px; /*滚动条的背景区域的圆角*/
    background-color: white; /*滚动条的背景颜色*/
  }

  /*定义滑块 内阴影+圆角*/
  ::-webkit-scrollbar-thumb {
    border-radius: 10px; /*滚动条的圆角*/
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    background-color: #2e363f; /*滚动条的背景颜色*/
  }

  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>
