<template>
  <div>
    <mavon-editor
      v-model="content"
      ref="md"
      @change="change"
      @imgAdd="$imgAdd"
      @imgDel="$imgDel"
      style="min-height: 600px"
    />

    <button @click="uploadimg">提交</button>
    <button @click="submit">提交</button>
  </div>
</template>

<script>
  // 导入组件 及 组件样式
  import {mavonEditor} from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  import RequestUtils from '../../utils/requestUtils'

  export default {
    // 注册
    components: {
      mavonEditor,
    },
    data() {
      return {
        content: '', // 输入的markdown
        html: '',    // 及时转的html
        configs: {},
        img_file: {} // 图片文件
      }
    },
    methods: {
      // 绑定@imgAdd event
      $imgAdd(pos, $file) {
        // 缓存图片信息
        this.img_file[pos] = $file;
      },
      $imgDel(pos) {
        delete this.img_file[pos];
      },
      /**
       * 将图片上传到服务器，返回地址替换到md中
       * 返回数据为 res = [[pos, url], [pos, url]...]
       * @param pos 写在md中的文件名
       * @param $file  File Object
       */
      uploadimg() {
        console.log("this.img_file:", this.img_file);
        // 第一步.将图片上传到服务器.
        var formdata = new FormData();
        for (var _img in this.img_file) {
          formdata.append("file", this.img_file[_img]);
        }
        RequestUtils.upload('admin-system/fileApi/batchUpload/markdown', formdata).then(res => {
          var resData = res.resData;
          console.log(resData);
          // 第二步.将返回的url替换到文本原位置
          for (var img in resData) {
            console.log("img[0]", resData[img].pos);
            console.log("img[1]", resData[img].fileUrl);
            this.$refs.md.$img2Url(resData[img].pos, resData[img].fileUrl);
          }
        }).catch(err => {
          console.log(err);
        })
      },
      // 所有操作都会被解析重新渲染
      change(value, render) {
        // render 为 markdown 解析后的结果[html]
        this.html = render;
      },
      // 提交
      submit() {
        console.info(this.content);
        console.info(this.html);
        this.$message.success('提交成功，已打印至控制台！');
      }
    }
  }
</script>

<style scoped>
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
