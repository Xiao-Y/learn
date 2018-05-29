<template>
  <el-row :gutter="10">
    <el-col :span="10">
      <el-input
        placeholder="输入关键字进行过滤"
        v-model="filterText">
      </el-input>
      <el-tree
        class="filter-tree"
        :data="data2"
        show-checkbox
        default-expand-all
        node-key="id"
        ref="tree2"
        :highlight-current="true"
        :props="defaultProps"
        :check-strictly="true"
        @node-click="changeCheck"
        :filter-node-method="filterNode">
      </el-tree>

    </el-col>
    <el-col :span="14">
      <el-row>
        <div class="buttons">
          <el-button @click="getCheckedNodes">通过 node 获取</el-button>
          <el-button @click="getCheckedKeys">通过 key 获取</el-button>
          <!--<el-button @click="setCheckedNodes">通过 node 设置</el-button>-->
          <!--<el-button @click="setCheckedKeys">通过 key 设置</el-button>-->
          <el-button @click="resetChecked">清空</el-button>
        </div>
      </el-row>

      <el-form :inline="true" ref="menu" label-width="80px" :model="menu">
        <el-form-item label="菜单标题">
          <el-input v-model="menu.title" readonly></el-input>
        </el-form-item>
        <el-form-item label="菜单CODE">
          <el-input v-model="menu.titleCode" readonly></el-input>
        </el-form-item>
        <el-form-item label="菜单路径">
          <el-input v-model="menu.path" readonly></el-input>
        </el-form-item>
        <el-form-item label="菜单图标">
          <el-input v-model="menu.icon" readonly></el-input>
        </el-form-item>
        <el-form-item label="有效标志">
          <el-input v-model="menu.validInd" readonly></el-input>
        </el-form-item>
      </el-form>

    </el-col>
  </el-row>
</template>

<script>
  export default {
    watch: {
      filterText(val) {
        this.$refs.tree2.filter(val);
      }
    },
    methods: {
      changeCheck(data, node, cchecked) {
        this.menu = data;
      },
      filterNode(value, data) {
        if (!value) return true;
        return data.title.indexOf(value) !== -1;
      },
      getCheckedNodes() {
        console.log(this.$refs.tree2.getCheckedNodes());
      },
      getCheckedKeys() {
        console.log(this.$refs.tree2.getCheckedKeys());
      },
      setCheckedNodes() {
        this.$refs.tree2.setCheckedNodes([{
          id: 5,
          label: '二级 2-1'
        }, {
          id: 9,
          label: '三级 1-1-1'
        }]);
      },
      setCheckedKeys() {
        this.$refs.tree2.setCheckedKeys([3]);
      },
      resetChecked() {
        this.$refs.tree2.setCheckedKeys([]);
      }
    },

    data() {
      return {
        filterText: '', //过滤条件
        menu: {
          id: '',
          pid: '',
          title: '',
          titleCode: '',
          path: '',
          icon: '',
          validInd: ''
        },
        data2: [{
          id: 1,
          title: '一级 1',
          path: 'EERR',
          titleCode: 'EERR',
          icon: 'EERR',
          validInd: 'true',
          pid: 22,
          children: [{
            id: 2,
            title: '二级 1-1',
            path: 'EERR1-1',
            titleCode: 'EERR1-1',
            icon: 'EERR1-1',
            validInd: 'true',
            pid: 22,
            children: [{
              id: 3,
              title: '三级 1-1-1',
              path: 'EERR1-1-1',
              titleCode: 'EERR1-1-1',
              icon: 'EERR1-1-1',
              validInd: 'true',
              pid: 22
            }, {
              id: 4,
              title: '三级 1-1-2',
              path: 'EERR1-1-2',
              titleCode: 'EERR1-1-2',
              icon: 'EERR1-1-2',
              validInd: 'true',
              pid: 22
            }]
          }]
        }, {
          id: 5,
          title: '二级 1-1',
          path: 'EERR1-1',
          titleCode: 'EERR1-1',
          icon: 'EERR1-1',
          validInd: 'true',
          pid: 22,
          children: [{
            id: 36,
            title: '三级 1-1-1',
            path: 'EERR1-1-1',
            titleCode: 'EERR1-1-1',
            icon: 'EERR1-1-1',
            validInd: 'true',
            pid: 22
          }, {
            id: 46,
            title: '三级 1-1-2',
            path: 'EERR1-1-2',
            titleCode: 'EERR1-1-2',
            icon: 'EERR1-1-2',
            validInd: 'true',
            pid: 22
          }]
        }],
        defaultProps: {
          children: 'children',
          label: 'title'
        }
      };
    }
  }
</script>

<style scoped>
  .el-row {
    margin-bottom: 10px;

  }
</style>
