<template>
  <div>
    <el-row>
      <el-collapse v-model="activeNames">
        <el-collapse-item name="1">
          <template slot="title">
            <b>查询条件</b><i class="el-icon-search"></i>
          </template>
          <el-form :model="queryFilter" ref="queryFilter" inline size="mini">
            <el-form-item label="商品编号" prop="spuNo">
              <el-input v-model="queryFilter.spuNo" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="商品名称" prop="goodsName">
              <el-input v-model="queryFilter.goodsName" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="分类名称" prop="categoryId">
              <custom-select v-model="queryFilter.categoryId"
                             :datasource="categorySelect"
                             placeholder="请选择商品分类">
              </custom-select>
            </el-form-item>
            <el-form-item label="品牌名称" prop="brandId">
              <custom-select v-model="queryFilter.brandId"
                             :datasource="brandSelect"
                             placeholder="请选择商品品牌">
              </custom-select>
            </el-form-item>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </el-row>
    <!-- 查询按钮组 -->
    <button-group-query @onAdd="handleAdd" @onQuery="loadDataList" :queryFilter="queryFilter"/>
    <el-row>
      <el-table :data="tableData" border stripe style="width:100%">
        <el-table-column label="商品编号" prop="spuNo" width="200"></el-table-column>
        <el-table-column label="商品名称" prop="goodsName" width="200"></el-table-column>
        <el-table-column label="最低售价" prop="lowPrice"></el-table-column>
        <el-table-column label="总库存量" prop="stock"></el-table-column>
        <el-table-column label="品牌" prop="brandId">
          <template slot-scope="scope">
            <custom-select v-model="scope.row.brandId"
                           :datasource="brandSelect"
                           :value-key="scope.row.id"
                           disabled placeholder="请选择商品品牌">
            </custom-select>
          </template>
        </el-table-column>
        <el-table-column label="是否有货" prop="stock" width="80">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.stock > 0 ? 'success' : 'danger'"
              disable-transitions>{{ scope.row.stock | productStatusName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="240">
          <template slot-scope="scope">
            <!--  操作按钮组 -->
            <button-group-option @onDel="handleDelete(scope.row,scope.$index)"
                                 @onEdit="handleEdit(scope.row,scope.$index)"
                                 @onInd="handleProhibit(scope.row,scope.$index)"
                                 :disInd="!scope.row.validInd"></button-group-option>
            <div style="float:left;margin-left:10px;">
              <el-tooltip class="item" effect="dark" content="查询SKU" placement="top-start" :open-delay="1500">
                <el-button type="success" size="mini" @click="handleSku(scope.row,scope.$index)">
                  <i class="el-icon-view"></i>
                </el-button>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
        <el-table-column type="expand" label="详细" width="50">
          <template slot-scope="scope">
            <el-form label-position="left" inline class="ms-table-expand" label-width="120px">
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
              <el-form-item label="分类名称">
                <custom-select v-model="scope.row.categoryId"
                               :datasource="categorySelect"
                               :value-key="scope.row.id"
                               disabled placeholder="请选择商品分类">
                </custom-select>
              </el-form-item>
              <el-form-item label="商品排序">
                <span>{{ scope.row.spuSort }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <!-- 分页组件  -->
    <custom-page :queryPage="queryFilter" @onQuery="loadDataList"></custom-page>
    <el-dialog :title="tableTitle" :visible.sync="dialogTableVisible" v-if="dialogTableVisible">
      <good-sku-list :spuId="spuId" :category-id="categoryId" v-if="dialogTableVisible"/>
    </el-dialog>
  </div>
</template>


<script>
import {FindListByPage, ProhibitById, DelById} from "../../api/product/GoodsSpuApi";
import {FindCategorySelect} from "../../api/product/GoodsCategoryApi";

// ===== 工具类 start
import VueUtils from "../../utils/vueUtils";
import pageMixins from "../../utils/pageMixins";

// ===== component start
import GoodSkuList from './GoodsSkuList.vue';
import CustomSelect from '../../components/common/CustomSelect.vue';

import ButtonGroupOption from '../../components/common/ButtonGroupOption.vue';
import ButtonGroupQuery from '../../components/common/ButtonGroupQuery.vue';
import CustomPage from '../../components/common/CustomPage.vue'
import {FindBrandSelect} from "../../api/product/GoodsBrandApi";

export default {
  name: "proGoodsSpuListIndex",
  components: {
    CustomSelect,
    GoodSkuList,
    ButtonGroupOption,
    ButtonGroupQuery,
    CustomPage
  },
  mixins: [pageMixins],
  data() {
    return {
      dialogTableVisible: false,// 打开SKU窗口
      tableTitle: '',// SPU name
      spuId: null,// 商品ID
      categoryId: null,// 商品分类id
      queryFilter: {
        // 查询条件
        spuNo: null,
        goodsName: null,
        categoryId: null
      },
      tableData: [],
      activeNames: ['1'],
      categorySelect: [],
      brandSelect: [],
    }
  },
  async created() {
    await FindCategorySelect().then(res => {
      this.categorySelect = res.resData;
    });
    await FindBrandSelect().then(res => {
      this.brandSelect = res.resData;
    });
    // 请数据殂
    this.loadDataList();
  },
  //每次激活时
  activated() {
    // 根据key名获取传递回来的参数，data 就是 map
    this.$bus.once('notifyInfo', function (data) {
      var index = this.tableData.findIndex(f => f.id === data.id);
      if (index != -1) { // 更新
        this.tableData.splice(index, 1, data);
      } else { // 添加
        this.tableData.push(data);
      }
    }.bind(this));
  },
  methods: {
    // 获取权限列表数据
    loadDataList() {
      FindListByPage(this.queryFilter).then(res => {
        var data = res.resData;
        this.tableData = data.tableData;
        this.queryFilter.recordCount = data.recordCount;
        this.queryFilter.totalPages = data.totalPages;
      });
    },
    // 添加权限
    handleAdd() {
      this.$router.push({
        name: 'proGoodsSpuEdit',
        query: {
          optionType: 'add',
          categorySelect: JSON.stringify(this.categorySelect),
          brandSelect: JSON.stringify(this.brandSelect)
        }
      });
    },
    handleEdit(row, index) {
      this.$router.push({
        name: 'proGoodsSpuEdit',
        query: {
          optionType: 'edit',
          goodsSpuEdit: JSON.stringify(row),
          categorySelect: JSON.stringify(this.categorySelect),
          brandSelect: JSON.stringify(this.brandSelect),
        }
      });
    },
    handleDelete(row, index) {
      var _this = this;

      VueUtils.confirmDel(row.goodsName, () => {
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

      VueUtils.confirmInd(row.goodsName, () => {
        ProhibitById(row.id).then(res => {
          row.validInd = res.resData.validInd;
          _this.$message({
            type: 'success',
            message: '禁用成功!'
          });
        });
      });
    },
    handleSku(row, index) {
      this.dialogTableVisible = true;
      this.spuId = row.id;
      this.categoryId = row.categoryId;
      this.tableTitle = row.goodsName;
    }
  },
  filters: {
    productStatusName(productStatus) {
      return productStatus > 0 ? '有货' : '无货';
    }
  }
}
</script>

