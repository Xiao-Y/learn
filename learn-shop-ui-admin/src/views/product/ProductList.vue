<template>
  <div>
    <el-row>
      <el-collapse v-model="activeNames">
        <el-collapse-item name="1">
          <template slot="title">
            <b>查询条件</b><i class="el-icon-search"></i>
          </template>
          <el-form :model="queryFilter" ref="queryFilter" :inline="true" size="mini">
            <el-form-item label="商品名称" prop="commodityName">
              <el-input v-model="queryFilter.commodityName" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="产地" prop="localityGrowth">
              <el-input v-model="queryFilter.localityGrowth" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="是否有货" prop="jobStatus">
              <el-input v-model="queryFilter.status" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </el-row>
    <el-button type="success" size="mini" @click="handleAdd" icon="el-icon-plus">添加</el-button>
    <el-button type="primary" size="mini" @click="onQuery" icon="el-icon-search">查询</el-button>
    <el-button type="danger" size="mini" @click="resetForm('queryFilter')" icon="el-icon-close">重置</el-button>
    <el-row>
      <el-table :data="tableData" border style="width:100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="商品ID：">
                <span>{{ props.row.id }}</span>
              </el-form-item>
              <el-form-item label="商品信息：">
                <span>{{ props.row.commodityInfo }}</span>
              </el-form-item>
              <el-form-item label="单价：">
                <span>{{ props.row.unitPrice }}</span>
              </el-form-item>
              <el-form-item label="规格：">
                <span>{{ props.row.spec }}</span>
              </el-form-item>
              <el-form-item label="是否有货：">
                <el-switch v-model="props.row.status" activeValue="1" inactiveValue="0" active-text="有货"
                           inactive-text="无货" disabled></el-switch>
              </el-form-item>
              <el-form-item label="销售数量：">
                <span>{{ props.row.quantity }}</span>
              </el-form-item>
              <el-form-item label="等级：">
                <span>{{ props.row.grade }}</span>
              </el-form-item>
              <el-form-item label="产地：">
                <span>{{ props.row.localityGrowth }}</span>
              </el-form-item>
              <el-form-item label="包装：">
                <span>{{ props.row.packing }}</span>
              </el-form-item>
              <el-form-item label="销售数量：">
                <span>{{ props.row.quantity }}</span>
              </el-form-item>
              <el-form-item label="商品图片名称：">
                <span>{{ props.row.img }}</span>
              </el-form-item>
              <el-form-item label="创建时间">
                <el-date-picker type="datetime" v-model="props.row.createTime" disabled></el-date-picker>
                <!--<span>{{ props.row.createTime }}</span>-->
              </el-form-item>
              <el-form-item label="更新时间">
                <el-date-picker type="datetime" v-model="props.row.updateTime" disabled></el-date-picker>
                <!--<span>{{ props.row.updateTime }}</span>-->
              </el-form-item>
              <el-form-item label="创建人：">
                <span>{{ props.row.creatorCode }}</span>
              </el-form-item>
              <el-form-item label="更新人：">
                <span>{{ props.row.updaterCode }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="商品名称" prop="commodityName" width="140"></el-table-column>
        <el-table-column label="单价" prop="unitPrice"></el-table-column>
        <el-table-column label="规格" prop="spec"></el-table-column>
        <el-table-column label="是否有货" prop="status" width="80">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.status === '1' ? 'success' : 'danger'"
              disable-transitions>{{scope.row.status | productStatusName}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="210">
          <template slot-scope="scope">
            <el-button
              icon="el-icon-edit"
              size="mini"
              type="warning"
              @click="handleEdit(scope.$index, scope.row)">
            </el-button>
            <el-button
              icon="el-icon-picture"
              size="mini"
              type="info"
              @click="handleImageEdit(scope.$index, scope.row)">
            </el-button>
            <el-button
              icon="el-icon-delete"
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <el-row>
      <template style="margin-bottom: 20px">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="queryFilter.pageNo"
          :page-sizes="[10, 20, 30, 40]"
          layout="total,sizes, prev, pager, next"
          :page-size="queryFilter.pageSize"
          :total="queryFilter.recordCount">
        </el-pagination>
      </template>
    </el-row>
  </div>
</template>


<script>
  import {LoadDataProductList, DeleteProductById} from "../../api/product/productMag";

  export default {
    data() {
      return {
        queryFilter: {
          // 分页数据
          pageNo: null,// 当前页码
          recordCount: null, // 总记录数
          pageSize: null,//每页要显示的记录数
          totalPages: null,// 总页数
          // 查询条件
          commodityName: null,
          localityGrowth: null,
          status: null
        },
        tableData: [],
        activeNames: ['1']
      }
    },
    created() {
      // 请数据殂
      this.loadDataProductList();
    },
    methods: {
      // 查询按钮
      onQuery() {
        // 从第1页开始
        this.queryFilter.pageNo = 1;
        // 请求数据
        this.loadDataProductList();
        // 关闭查询折叠栏
//        this.activeNames = [];
      },
      // 清除查询条件
      resetForm(queryFilter) {
        this.$refs[queryFilter].resetFields();
      },
      // 请服务器数据（获取自动任务列表数据）
      loadDataProductList() {
        LoadDataProductList(this.queryFilter).then(res => {
          var data = res.resData;
          // 填充数据
          this.tableData = data.tableData;
          this.queryFilter.recordCount = data.recordCount;
          this.queryFilter.totalPages = data.totalPages;
        });

      },
      // 翻页
      handleCurrentChange(val) {
        this.queryFilter.pageNo = val;
        this.loadDataProductList();
      },
      // 改变页面大小
      handleSizeChange(val) {
        this.queryFilter.pageSize = val;
        this.loadDataProductList();
      },
      // 添加商品
      handleAdd() {
        this.$router.push({
          name: 'proProductEdit',
          params: {
            optionType: 'add'
          }
        });
      },
      handleEdit(index, row) {
        this.$router.push({
          name: 'proProductEdit',
          params: {
            optionType: 'edit',
            productEdit: row
          }
        });
      },
      handleImageEdit(index, row) {
        this.$router.push({
          name: 'proProductImageEdit',
          params: {
            productInfo: row
          }
        });
      },
      handleDelete(index, row) {
        var _this = this;
        _this.$confirm('此操作将删除该商品 ' + row.commodityName + ' 信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          DeleteProductById(row.id).then(res => {
            _this.tableData.splice(index, 1);
            _this.$message({
              type: 'success',
              message: '删除成功!'
            });
          });
        }).catch((err) => {
          _this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }

    },
    filters: {
      productStatusName(productStatus) {
        return productStatus === '1' ? '有货' : '无货';
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
