import {
  Message,
  MessageBox
} from 'element-ui'

var VueUtils = {
  // /* ================ 深拷贝,不含有 function 可用 ================ */
  deepCloneJson: function (initalObj) {
    var obj = {};
    try {
      obj = JSON.parse(JSON.stringify(initalObj));
    } catch (e) {
      console.error(e);
    }
    return obj;
  },

  /* ================ 深拷贝 ================ */
  deepClone: function deepClone(initalObj, finalObj) {
    // var obj = finalObj || {};
    // for (var i in initalObj) {
    //   var prop = initalObj[i];
    //   // 避免相互引用对象导致死循环，如initalObj.a = initalObj的情况
    //   if (prop === obj) {
    //     continue;
    //   }
    //   if (prop != null && typeof prop === 'object') {
    //     obj[i] = (prop.constructor === Array) ? [] : Object.create(prop);
    //   } else {
    //     obj[i] = prop;
    //   }
    // }
    // return obj;
    return this.deepClone3(initalObj);
  },
  deepClone2: function deepClone(obj) {
    //声明一个变量data用来保存深复制后的数据
    let data;
    //判断该参数是否存在且是Object类型
    if (obj && typeof obj === Object) {
      //声明一个变量,并判断他是array还是object
      data = Array.isArray(obj) ? [] : {};
      //便利该对象
      for (key in obj) {
        //如果该obj的孩子也是一个对象，那么递归调用deepClone
        if (typeof key === Object) {
          data[key] = deepClone(obj[key])
        } else {
          data[key] = obj[key]
        }
      }

    } else {
      data = obj;
    }
    return data;
  },
  deepClone3: function deepClone(obj) {
    if (!obj) {
      return obj;
    }
    let sourceCopy = obj instanceof Array ? [] : {};
    for (let item in obj) {
      sourceCopy[item] = typeof obj[item] === 'object' ? deepClone(obj[item]) : obj[item]
    }
    return sourceCopy;
  },
  /**
   * 元素进行类型判断
   * @param 元素
   * @returns 元素类型
   */
  getType: function (obj) {
    //tostring会返回对应不同的标签的构造函数
    var toString = Object.prototype.toString;
    var map = {
      '[object Boolean]': 'boolean',
      '[object Number]': 'number',
      '[object String]': 'string',
      '[object Function]': 'function',
      '[object Array]': 'array',
      '[object Date]': 'date',
      '[object RegExp]': 'regExp',
      '[object Undefined]': 'undefined',
      '[object Null]': 'null',
      '[object Object]': 'object'
    };
    if (obj instanceof Element) {
      return 'element';
    }
    return map[toString.call(obj)];
  },
  /**
   * 查询指定节点的所有父级菜单
   * @param menuData 数据源
   * @param currentNodeParentId 当前节点的父ID
   * @returns {Array} 返回所有父级节点数据，包含自己在最后一位
   */
  getParent: function (menuData, currentNodeParentId) {
    var arrRes = [];
    if (menuData.length == 0) {
      if (!!currentNodeParentId) {
        arrRes.unshift(menuData);
      }
      return arrRes;
    }
    let rev = (data, nodeId) => {
      for (var i = 0, length = data.length; i < length; i++) {
        let node = data[i];
        if (node.id == nodeId) {
          arrRes.unshift(node)
          rev(menuData, node.pid);
          break;
        } else {
          if (!!node.children) {
            rev(node.children, nodeId);
          }
        }
      }
      return arrRes;
    };
    arrRes = rev(menuData, currentNodeParentId);
    return arrRes;
  },
  /**
   * 查询指定节点的所有父级菜单
   * @param menuData 数据源
   * @param currentNodeId 当前节点
   * @returns {Array} 返回所有父级节点数据
   */
  getParentByCurrentNodeId: function (menuData, currentNodeId) {
    var nodeData = this.getParent(menuData, currentNodeId);
    nodeData.pop();
    return nodeData;
  },
  /**
   * 删除前的确认提示
   * @param info 删除的名称
   * @param callback 回调
   */
  confirmDel(info, callback) {
    var tips = '此操作将删除 ' + info + ' ,是否继续?';
    this.confirm(callback, {tips: tips, title: '警告', cancelMessage: '已取消删除'});
  },
  /**
   * 禁用前的确认提示
   * @param info 禁用的名称
   * @param callback 回调
   */
  confirmInd(info, callback) {
    var tips = '此操作将禁用 ' + info + ' ,是否继续?';
    this.confirm(callback, {tips: tips, title: '警告', cancelMessage: '已取消禁用'});
  },
  /**
   * 确认提示
   * @param callback
   * @param option: <br/>
   * { <br/>
   * tips:'确定此操作', //提示信息 <br/>
   * title:'提示', // 弹出框标题 <br/>
   * confirmButtonText:'确定',  // 确认按钮显示 <br/>
   * cancelButtonText:'取消',  // 取消按钮显示 <br/>
   * confirmType:'warning',  // 弹出框图标 <br/>
   * cancelType:'info',  // 取消后提示图标 <br/>
   * cancelMessage:'已取消此操作'  // 取消后提示信息 <br/>
   * } <br/>
   */
  confirm(callback, option) {
    if (!option) {
      option = {};
    }
    // 提示信息
    var tips = option.tips ? option.tips : '确定此操作';
    // 弹出框标题
    var title = option.title ? option.title : '提示';
    // 确认按钮显示
    var confirmButtonText = option.confirmButtonText ? option.confirmButtonText : '确定';
    // 取消按钮显示
    var cancelButtonText = option.cancelButtonText ? option.cancelButtonText : '取消';
    // 弹出框图标
    var confirmType = option.confirmType ? option.confirmType : 'warning';
    // 取消后提示图标
    var cancelType = option.cancelType ? option.cancelType : 'info';
    // 取消后提示信息
    var cancelMessage = option.cancelMessage ? option.cancelMessage : '已取消此操作';

    MessageBox.confirm(tips, title, {
      confirmButtonText: confirmButtonText,
      cancelButtonText: cancelButtonText,
      type: confirmType
    }).then(() => {
      callback();
    }).catch((err) => {
      console.error(err);
      Message({
        type: cancelType,
        message: cancelMessage
      });
    });
  }
}

export default VueUtils
