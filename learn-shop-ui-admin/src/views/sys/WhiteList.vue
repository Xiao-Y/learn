<template>
  <div>
    <el-row>
      <el-collapse value="1">
        <el-collapse-item title="查询条件" name="1">
          <el-form inline :model="queryForm" ref="queryFilter" class="query-form-filter" size="mini">
            <el-row>
              <el-form-item label="审批人" prop="user">
                <el-input v-model="queryForm.user" placeholder="审批人"></el-input>
              </el-form-item>
              <el-form-item label="审批人" prop="user">
                <el-input v-model="queryForm.user" placeholder="审批人"></el-input>
              </el-form-item>
              <el-form-item label="审批人" prop="user">
                <el-input v-model="queryForm.user" placeholder="审批人"></el-input>
              </el-form-item>
              <el-form-item label="审批人" prop="user">
                <el-input v-model="queryForm.user" placeholder="审批人"></el-input>
              </el-form-item>
              <el-form-item label="审批人" prop="user">
                <el-input v-model="queryForm.user" placeholder="审批人"></el-input>
              </el-form-item>
              <el-form-item label="活动区域" prop="region">
                <el-select v-model="queryForm.region" placeholder="活动区域">
                  <el-option label="区域一" value="shanghai"></el-option>
                  <el-option label="区域二" value="beijing"></el-option>
                </el-select>
              </el-form-item>
            </el-row>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </el-row>
    <button-group-query :queryFilter="queryForm"></button-group-query>
    <el-row>
      <template>
        <el-table :data="tableData" border stripe >
          <el-table-column fixed prop="date" label="日期" width="150">
          </el-table-column>
          <el-table-column prop="name" label="姓名" width="120">
          </el-table-column>
          <el-table-column prop="province" label="省份" width="120">
          </el-table-column>
          <el-table-column prop="city" label="市区" width="120">
          </el-table-column>
          <el-table-column prop="address" label="地址">
          </el-table-column>
          <el-table-column prop="zip" label="邮编" width="120">
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <template slot-scope="scope">
              <!-- <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
              <el-button type="text" size="small">编辑</el-button> -->
              <button-group-option @onDel="onDel(scope.row,scope.$index)" @onEdit="onEdit(scope.row,scope.$index)"
                                   :disInd="!scope.row.valid"
                                   @onInd="onInd(scope.row,scope.$index)"></button-group-option>
            </template>
          </el-table-column>
        </el-table>
      </template>
    </el-row>
    <custom-page :queryPage="queryForm"></custom-page>
  </div>
</template>

<script>
  import ButtonGroupOption from '../../components/common/ButtonGroupOption.vue';
  import ButtonGroupQuery from '../../components/common/ButtonGroupQuery.vue';
  import CustomPage from '../../components/common/CustomPage.vue';


  export default {
    name: 'sysWhiteListIndex',
    components: {
      ButtonGroupOption,
      ButtonGroupQuery,
      CustomPage
    },

    data() {
      return {
        queryForm: {
          // 分页数据
          pageNo: 1, // 当前页码
          recordCount: 200, // 总记录数
          pageSize: 10, //每页要显示的记录数
          totalPages: 20, // 总页数
        },
        tableData: [{
          date: '2016-05-03',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1518 弄',
          valid: false,
          zip: 200333
        }, {
          date: '2016-05-02',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1518 弄',
          valid: true,
          zip: 200333
        }, {
          date: '2016-05-04',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1518 弄',
          valid: true,
          zip: 200333
        }, {
          date: '2016-05-01',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          valid: true,
          address: '上海市普陀区金沙江路 1518 弄',
          zip: 200333
        }]
      };
    },
    created() {

    },
    methods: {
      onInd(row, index) {
        row.valid = false;
        console.info("onInd");
        console.info("row.address", row.address);
        console.info("index", index);
      },
      onDel(row, index) {
        console.info("onDel");
        console.info("row.address", row.address);
        console.info("index", index);
      },
      onEdit(row, index) {
        console.info("onEdit");
        console.info("row.address", row.address);
        console.info("index", index);
      }
    }
  }
</script>

