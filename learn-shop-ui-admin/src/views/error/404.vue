<template>
  <div id="main">
    <!-- header -->
    <div id="header">
      <h1>Page Gone To Heaven And Doesn't Want To Come Back<span>404 Error - Not Found </span></h1>
    </div>
    <!-- content -->
    <div id="content">
      <ul class="nav">
        <li class="home">
          <router-link to="/home/index">返回首页</router-link>
        </li>
        <li class="site_map">
          <a href="#" @click="goBack">返回上页</a>
        </li>
        <li class="search">
          <a href="#" @click="goBack">我要反馈</a>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
  export default {
    data: function () {
      return {}
    },
    methods: {
      goBack() {
        window.history.length > 1 ? this.$router.go(-1) : this.$router.push({name:'homeIndex'})
      }
    }
  }
</script>

<style scoped>
  * {
    margin: 0;
    padding: 0;
  }

  html, body {
    height: 100%;
  }

  body {
    background: #fff;
    font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
    font-size: 100%;
    line-height: 1.25em;
    color: #b2b3c4;
    font-style: italic;
  }

  img {
    border: 0;
    vertical-align: top;
    text-align: left;
  }

  ul, ol {
    list-style: none;
  }

  /*==== GLOBAL =====*/
  #main {
    width: 100%;
    height: 100%;
    margin: 0 auto;
    background: url(/static/img/error/404.jpg) no-repeat left top;
    font-size: 1.125em;
    overflow: hidden;
    position: relative;
  }

  #header {
    height: 150px;
  }

  #content {
    min-height: 457px;
    height: auto !important;
    height: 457px;
  }

  p {
    margin-bottom: 8px;
    text-align: center;
    padding: 0 20px;
    letter-spacing: -1px;
  }

  /*----- txt, links, lines, titles -----*/
  a {
    color: #8e8fc9;
    outline: none;
  }

  a:hover {
    text-decoration: none;
  }

  h1 {
    font-size: 40px;
    line-height: 1.2em;
    font-weight: normal;
    color: #8e8fc9;
    text-align: center;
    padding: 80px 0 0 0;
    text-transform: capitalize;
    letter-spacing: -2px;
  }

  h1 span {
    display: block;
    font-size: 24px;
    line-height: 25px;
    font-variant: normal;
    text-transform: none;
    letter-spacing: -1px;
    font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  }

  /*===== content =====*/
  #content .nav {
    position: relative;
    height: 360px;
  }

  #content .nav li {
    position: absolute;
    font-size: 24px;
    line-height: 1.2em;
    font-weight: bold;
    text-transform: uppercase;
    font-weight: normal;
    letter-spacing: -1px;
    text-transform: capitalize;
  }

  #content .nav li.home {
    left: 445px;
    top: 270px;
  }

  #content .nav li.site_map {
    left: 120px;
    top: 65px;
  }

  #content .nav li.search {
    right: 90px;
    top: 65px;
  }

  #content .nav li a {
    color: #a8a9d4;
    text-decoration: none;
  }

  #content .nav li a:hover {
    text-decoration: underline;
  }
</style>
