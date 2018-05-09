'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  BASE_API: '"https://easy-mock.com/mock/5af2c3514b7b62162e8fc3cd/shop"',
  // BASE_API: '"https://easy-mock.com/mock/5a72c1ecc76727050336e0bc/mdm/"',
})
