<template>
  <div>
    <mavon-editor
      ref="md"
      v-model="content"
      @save="submit"
      @change="change"
      @imgAdd="imgAdd"
      @imgDel="imgDel"
      style="min-height: 400px"/>
    <el-button type="primary" @click="submit" size="mini">确定</el-button>
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
    props: {
      markdown: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        content: '', // 输入的markdown
        html: '',    // 及时转的html
        configs: {},
        imgFile: [] // 图片文件
      }
    },
    created() {
      this.content = this.markdown;
    },
    methods: {
      imgAdd(pos, $file) {
        // 缓存图片信息
        this.imgFile[pos] = $file;
      },
      imgDel(pos) {
        delete this.imgFile[pos];
      },
      /**
       * 将图片上传到服务器，返回地址替换到md中
       * 返回数据为 res = [[pos, url], [pos, url]...]
       * @param pos 写在md中的文件名
       * @param $file  File Object
       */
      uploadimg(callback) {
        // console.log("this.imgFile:", this.imgFile);
        // 第一步.将图片上传到服务器.
        var formdata = new FormData();
        for (var _img in this.imgFile) {
          formdata.append("file", this.imgFile[_img]);
        }
        RequestUtils.upload('admin-system/fileApi/batchUpload/markdown', formdata).then(res => {
          var resData = res.resData;
          // console.log(resData);
          // 第二步.将返回的url替换到文本原位置
          for (var img in resData) {
            // console.log("img[0]", resData[img].pos);
            // console.log("img[1]", resData[img].fileUrl);
            this.$refs.md.$img2Url(resData[img].pos + 1, resData[img].fileUrl);
          }
          callback();
        }).catch(err => {
          console.log(err);
        })
      },
      // 所有操作都会被解析重新渲染，render 为 markdown 解析后的结果[html]
      change(value, render) {
        this.html = render;
      },
      // 提交
      submit() {
        if (this.imgFile && this.imgFile.length > 0) {
          this.uploadimg(() => {
            this.$emit('markdownReturn', {content: this.content, html: this.html});
          });
        } else {
          this.$emit('markdownReturn', {content: this.content, html: this.html});
        }
        console.info(this.content);
        // console.info(this.html);
        // this.$message.success('提交成功，已打印至控制台！');
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
