var mixin = {
  data: function () {
    return {
      queryFilter: {
        // 分页数据
        pageNo: 1, // 当前页码
        recordCount: 0, // 总记录数
        pageSize: 10, //每页要显示的记录数
        totalPages: 0, // 总页数
      }
    }
  }
};

export default mixin;
