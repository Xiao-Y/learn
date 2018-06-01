var VueUtils = {
  // /* ================ 深拷贝 ================ */
  // deepClone: function (initalObj) {
  //   var obj = {};
  //   try {
  //     obj = JSON.parse(JSON.stringify(initalObj));
  //   }catch(e) {}
  //   return obj;
  // }

  /* ================ 深拷贝 ================ */
  deepClone: function deepClone(initalObj, finalObj) {
    var obj = finalObj || {};
    for (var i in initalObj) {
      var prop = initalObj[i];
      // 避免相互引用对象导致死循环，如initalObj.a = initalObj的情况
      if (prop === obj) {
        continue;
      }
      if (prop != null && typeof prop === 'object') {
        obj[i] = (prop.constructor === Array) ? [] : Object.create(prop);
      } else {
        obj[i] = prop;
      }
    }
    return obj;
  }
}

export default VueUtils
