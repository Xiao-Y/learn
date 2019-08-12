'use strict'
// Template version: 1.3.1
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path')

module.exports = {
  dev: {
    // Paths
    assetsSubDirectory: 'static', // 静态资源文件夹
    assetsPublicPath: '/', // 发布路径
    proxyTable: {
      '/public-auth': {
        target: 'http://127.0.0.1:8771', // 接口的域名
        changeOrigin: true // 如果接口跨域，需要进行这个参数配置
        // pathRewrite: {
        //   '^/public-auth': '' //将前缀 '/api' 转为 ''
        // }
      },
      '/admin-system': {
        target: 'http://127.0.0.1:8771', // 接口的域名
        changeOrigin: true // 如果接口跨域，需要进行这个参数配置
        // pathRewrite: {
        //   '^/admin-system': '' //将前缀 '/api' 转为 ''
        // }
      },
      '/admin-user': {
        target: 'http://127.0.0.1:8771', // 接口的域名
        changeOrigin: true // 如果接口跨域，需要进行这个参数配置
        // pathRewrite: {
        //   '^/admin-user': '' //将前缀 '/api' 转为 ''
        // }
      },
      '/public-job': {
        target: 'http://127.0.0.1:8771', // 接口的域名
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
        // pathRewrite: {
        //   '^/public-job': '' //将前缀 '/api' 转为 ''
        // }
      },
      '/core-product': {
        target: 'http://127.0.0.1:8771', // 接口的域名
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
        // pathRewrite: {
        //   '^/core-product': '' //将前缀 '/api' 转为 ''
        // }
      },
      '/dataRecovery': {
        target: 'http://127.0.0.1:8771', // 接口的域名
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
        // pathRewrite: {
        //   '^/core-product': '' //将前缀 '/api' 转为 ''
        // }
      }
    },

    // Various Dev Server settings
    host: 'localhost', // can be overwritten by process.env.HOST
    port: 8080, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
    autoOpenBrowser: false,
    errorOverlay: true,
    notifyOnErrors: true,
    poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-

    // Use Eslint Loader?
    // If true, your code will be linted during bundling and
    // linting errors and warnings will be shown in the console.
    useEslint: true,
    // If true, eslint errors and warnings will also be shown in the error overlay
    // in the browser.
    showEslintErrorsInOverlay: false,

    /**
     * Source Maps
     */

    // https://webpack.js.org/configuration/devtool/#development
    devtool: 'cheap-module-eval-source-map',

    // If you have problems debugging vue-files in devtools,
    // set this to false - it *may* help
    // https://vue-loader.vuejs.org/en/options.html#cachebusting
    cacheBusting: true,

    cssSourceMap: true
  },

  build: {
    // Template for index.html
    index: path.resolve(__dirname, '../dist/index.html'),

    // Paths
    assetsRoot: path.resolve(__dirname, '../dist'),
    assetsSubDirectory: 'static',
    assetsPublicPath: './',

    /**
     * Source Maps
     */

    productionSourceMap: false,
    // https://webpack.js.org/configuration/devtool/#production
    devtool: '#source-map',

    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to:
    // npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],

    // Run the build command with an extra argument to
    // View the bundle analyzer report after build finishes:
    // `npm run build --report`
    // Set to `true` or `false` to always turn it on or off
    bundleAnalyzerReport: process.env.npm_config_report
  }
}
