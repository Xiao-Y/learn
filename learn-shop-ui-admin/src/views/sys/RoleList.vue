<template>
  <div>
    <el-row>
      <el-collapse value="1">
        <el-collapse-item title="查询条件" name="1">
          <!-- <el-form :inline="true" :model="queryFilter" class="demo-form-inline">
            <el-row>
              <el-form-item label="审批人">
                <el-input v-model="queryForm.user" placeholder="审批人"></el-input>
              </el-form-item>
              <el-form-item label="审批人">
                <el-input v-model="queryForm.user" placeholder="审批人"></el-input>
              </el-form-item>
              <el-form-item label="审批人">
                <el-input v-model="queryForm.user" placeholder="审批人"></el-input>
              </el-form-item>
              <el-form-item label="审批人">
                <el-input v-model="queryForm.user" placeholder="审批人"></el-input>
              </el-form-item>
              <el-form-item label="审批人">
                <el-input v-model="queryForm.user" placeholder="审批人"></el-input>
              </el-form-item>
              <el-form-item label="活动区域">
                <el-select v-model="queryForm.region" placeholder="活动区域">
                  <el-option label="区域一" value="shanghai"></el-option>
                  <el-option label="区域二" value="beijing"></el-option>
                </el-select>
              </el-form-item>
            </el-row>
            <el-row>
              <div class="buttons">
                <el-button type="primary" size="mini" @click="onQuery">查询</el-button>
              </div>
            </el-row>
          </el-form> -->
        </el-collapse-item>
      </el-collapse>
    </el-row>
    <el-row>
      <template>
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column label="角色名称" prop="roleName"></el-table-column>
          <el-table-column label="角色CODE" prop="roleCode"></el-table-column>
          <el-table-column label="角色描述" prop="descritpion"></el-table-column>  
          <el-table-column type="expand" label="详细" width="50">
            <template slot-scope="props">
              <el-table :data="props.row.children" stripe style="width: 100%">
                <el-table-column label="创建时间">
                  <template slot-scope="scope">
                    <el-date-picker type="datetime" v-model="scope.row.createTime" readonly></el-date-picker>
                  </template>
                </el-table-column>
                <el-table-column label="创建人" prop="creatorCode"></el-table-column>
                <el-table-column label="更新时间">
                  <template slot-scope="scope">
                    <el-date-picker type="datetime" v-model="scope.row.updateTime" readonly></el-date-picker>
                  </template>  
                </el-table-column>  
                <el-table-column label="创建人" prop="creatorCode"></el-table-column>
              </el-table>
            </template>
          </el-table-column>      
          <el-table-column fixed="right" label="操作" width="100">
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
              <el-button type="text" size="small">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
      </template>
    </el-row>
    <el-row>
      <template>
        <div class="block">
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
        </div>
      </template>
    </el-row>
  </div>
</template>

<script>
//  import {Message} from "element-ui";
  import {LoadDataRoleList, DeleteRoleById} from "../../api/sys/roleMag";

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
      this.LoadDataRoleList();
    },
    methods: {
      // 查询按钮
      onQuery() {
        // 从第1页开始
        // this.queryFilter.pageNo = 1;
        // 请求数据
        this.LoadDataRoleList();
        // 关闭查询折叠栏
//        this.activeNames = [];
      },
      // 清除查询条件
      resetForm(queryFilter) {
        this.$refs[queryFilter].resetFields();
      },
      // 请服务器数据（获取自动任务列表数据）
      LoadDataRoleList() {
        LoadDataRoleList(this.queryFilter).then(res => {
          var data = res.resData;
          // 填充数据
          if(data.content){
            for(var ind in data.content){
              var childrenData = [];
              if (data.content[ind]) {
                childrenData.push(data.content[ind]);
              }
              this.$set(data.content[ind],'children',childrenData);
            }
          }
          console.info(data.content);
          this.tableData = data.content;
          this.queryFilter.recordCount = data.totalElements;
          this.queryFilter.totalPages = data.totalPages;
        });

      },
      // 翻页
      handleCurrentChange(val) {
        this.queryFilter.pageNo = val;
        this.LoadDataRoleList();
      },
      // 改变页面大小
      handleSizeChange(val) {
        this.queryFilter.pageSize = val;
        this.LoadDataRoleList();
      },
      // 添加商品
      handleAdd() {
        this.$router.push({
          name: 'productEdit',
          params: {
            optionType: 'add'
          }
        });
      },
      handleEdit(index, row) {
        this.$router.push({
          name: 'productEdit',
          params: {
            optionType: 'edit',
            productEdit: row
          }
        });
      },
      handleImageEdit(index, row) {
        this.$router.push({
          name: 'productImageEdit',
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
          DeleteRoleById(row.id).then(res => {
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
</style>