<template>
  <div>
    <el-row>
      <el-collapse value="1">
        <el-collapse-item name="1">
          <template slot="title">
            <b>查询条件</b><i class="el-icon-search"></i>
          </template>
          <el-form :inline="true" :model="queryFilter" ref="queryFilter" class="demo-form-inline" size="mini">
            <el-row>
              <el-form-item label="ID" prop="id">
                <el-input v-model="queryFilter.id" placeholder="ID"></el-input>
              </el-form-item>
              <el-form-item label="KEY" prop="key">
                <el-input v-model="queryFilter.key" placeholder="KEY"></el-input>
              </el-form-item>
              <el-form-item label="名称" prop="name">
                <el-input v-model="queryFilter.name" placeholder="名称"></el-input>
              </el-form-item>
              <el-form-item label="部署ID" prop="deploymentId">
                <el-input v-model="queryFilter.deploymentId" placeholder="名称"></el-input>
              </el-form-item>
            </el-row>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </el-row>
    <!-- 查询按钮组 -->
    <button-group-query @onAdd="handleAdd" @onQuery="loadDataList" :queryFilter="queryFilter" :show-add="false"></button-group-query>
    <div style="display: inline-block;margin-left:10px;">
      <el-button type="success" size="mini" @click="startDeploy" icon="el-icon-mouse">部署</el-button>
    </div>
    <el-row>
      <template>
        <el-table border style="width: 100%" ref="procDefListRef"
                  :data="tableData"
                  row-key="id">
          <el-table-column label="ID" prop="id" width="210"></el-table-column>
          <el-table-column label="KEY" prop="key"></el-table-column>
          <el-table-column label="分组" prop="category"></el-table-column>
          <el-table-column label="名称" prop="name"></el-table-column>
          <el-table-column label="版本" prop="version"></el-table-column>
          <el-table-column label="部署ID" prop="deploymentId"></el-table-column>
          <el-table-column type="expand" label="详细" width="50">
            <template slot-scope="scope">
              <el-form label-position="left" inline class="demo-table-expand" label-width="120px">
                <el-form-item label="资源路径">
                  <span>{{ scope.row.resourceName }}</span>
                </el-form-item>
                <el-form-item label="图片路径">
                  <span>{{ scope.row.diagramResourceName }}</span>
                </el-form-item>
                <el-form-item label="描述">
                  <span>{{ scope.row.description }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="80">
            <template slot-scope="scope">
              <!--  操作按钮组 -->
              <button-group-option @onDel="handleDelete(scope.row,scope.$index)"
                                   @onEdit="handleEdit(scope.row,scope.$index)"
                                   @onInd="handleProhibit(scope.row,scope.$index)"
                                   :show-ind="false" :show-edit="false"></button-group-option>
            </template>
          </el-table-column>
        </el-table>
      </template>
    </el-row>
    <!-- 分页组件  -->
    <custom-page :queryPage="queryFilter" @onQuery="loadDataList"></custom-page>
    <el-dialog title="选取部署文件" :visible.sync="dialogVisible" width="30%">
      <el-upload
        ref="upload"
        :action="action"
        :on-success="uploadSuccess"
        :on-error="uploadError"
        :before-upload="checkFile"
        :file-list="fileList"
        multiple
        :auto-upload="false">
        <el-button slot="trigger" size="mini" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px;" size="mini" type="success" @click="submitUpload">部署流程</el-button>
        <div slot="tip">只能上传xml/zip文件，且不超过500kb</div>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script>
  // ===== api start
  import {
    LoadDataProcDefList,
    ProhibitProcDefById,
    DeleteProcDefById
  } from "../../api/proc/procDefMag";
  import {
    LoadSysDataDictionary
  } from "../../api/sys/DataDictionaryMag";
  // ===== component start
  import ButtonGroupOption from '../../components/common/ButtonGroupOption.vue';
  import ButtonGroupQuery from '../../components/common/ButtonGroupQuery.vue';
  import CustomPage from '../../components/common/CustomPage.vue'
  // ===== 工具类 start
  import VueUtils from "../../utils/vueUtils";
  import pageMixins from "../../utils/pageMixins";

  export default {
    components: {
      ButtonGroupOption,
      ButtonGroupQuery,
      CustomPage
    },
    mixins: [pageMixins],
    data() {
      return {
        queryFilter: {
          // 查询条件
          id: null,
          key: null,
          name: null,
          deploymentId: null,
        },
        tableData: [], // 列表数据源
        dialogVisible: false,
        fileList: [], // 上传文件列表
        action:'admin-system/actDeployApi/deploy/file',
      }
    },
    created() {
      // 请数据殂
      this.loadDataList();
    },
    //每次激活时
    activated() {
      // 根据key名获取传递回来的参数，data 就是 map
      this.$bus.once('procDefInfo', function (data) {
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
        LoadDataProcDefList(this.queryFilter).then(res => {
          var data = res.resData;
          this.tableData = data.content;
          this.queryFilter.recordCount = data.totalElements;
          this.queryFilter.totalPages = data.totalPages;
        });
      },
      // 添加权限
      handleAdd() {
        this.$router.push({
          name: 'sysProcDefEdit',
          query: {
            optionType: 'add',
            mailTypeSelect: JSON.stringify(this.mailTypeSelect),
            dataSourcesSelect: JSON.stringify(this.dataSourcesSelect)
          }
        });
      },
      handleEdit(row, index) {
        this.$router.push({
          name: 'sysProcDefEdit',
          query: {
            optionType: 'edit',
            id: row.id,
            mailTypeSelect: JSON.stringify(this.mailTypeSelect),
            dataSourcesSelect: JSON.stringify(this.dataSourcesSelect)
          }
        });
      },
      handleDelete(row, index) {
        var _this = this;

        VueUtils.confirmDel(row.mailCode, () => {
          DeleteProcDefById(row.id).then(res => {
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

        VueUtils.confirmInd(row.mailCode, () => {
          ProhibitProcDefById(row.id).then(res => {
            row.validInd = res.resData.validInd;
            _this.$message({
              type: 'success',
              message: '禁用成功!'
            });
          });
        });
      },
      startDeploy() {
        this.dialogVisible = true;
      },
      // 检查上传的文件类型
      checkFile(file) {
        const fileType = file.type;
        const isXml = fileType === 'text/xml';
        const isZip = fileType === 'application/x-zip-compressed';
        console.log("file type:", file.type);
        console.log("isXml:", isXml);
        console.log("isZip:", isZip);
        if (!isXml && !isZip) {
          this.$message.error('上传的文件类型只能是 XML 或者 ZIP 格式!');
        }
        return (isXml || isZip);
      },
      submitUpload() {
        console.log("submitUpload");
        this.$refs.upload.submit();
        console.log("submitUpload...");
      },
      uploadSuccess(res, file, fileList) {
        if (res.resData) {
          this.$notify({
            title: '成功',
            message: file.name + ' 部署成功！',
            position: 'bottom-right',
            type: 'success'
          });
        } else {
          this.$notify.error({
            title: '错误',
            position: 'bottom-right',
            message: file.name + ' 部署失败！'
          });
        }
        console.log(res);
        // console.log("uploadSuccess");
        this.fileList = [];
        this.dialogVisible = false;
      },
      uploadError(err, file, fileList) {

      }
    }
  }
</script>

<style scoped>
  /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
  ::-webkit-scrollbar {
    width: 3px;
    /*滚动条宽度*/
    height: 3px;
    /*滚动条高度*/
  }

  /*定义滚动条轨道 内阴影+圆角*/
  ::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    border-radius: 10px;
    /*滚动条的背景区域的圆角*/
    background-color: white;
    /*滚动条的背景颜色*/
  }

  /*定义滑块 内阴影+圆角*/
  ::-webkit-scrollbar-thumb {
    border-radius: 10px;
    /*滚动条的圆角*/
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    background-color: #2e363f;
    /*滚动条的背景颜色*/
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
