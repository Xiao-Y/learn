module.exports = {
    assetsDir: 'static',
    publicPath: '', // 设置打包文件相对路径
    devServer: {
        host: '127.0.0.1', // can be overwritten by process.env.HOST
        port: 8081, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
        // open: true, //配置自动启动浏览器
        proxy: {
            '/public-auth': {
                target: 'http://127.0.0.1:8771', // 接口的域名
                changeOrigin: true // 如果接口跨域，需要进行这个参数配置
            },
            '/admin-system': {
                target: 'http://127.0.0.1:8771', // 接口的域名
                changeOrigin: true // 如果接口跨域，需要进行这个参数配置
            },
            '/admin-user': {
                target: 'http://127.0.0.1:8771', // 接口的域名
                changeOrigin: true // 如果接口跨域，需要进行这个参数配置
            },
            '/public-job': {
                target: 'http://127.0.0.1:8771', // 接口的域名
                changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
            },
            '/core-product': {
                target: 'http://127.0.0.1:8771', // 接口的域名
                changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
            },
            '/dataRecovery': {
                target: 'http://127.0.0.1:8771', // 接口的域名
                changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
            }
        }
    }
}