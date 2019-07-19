import EventBus from '@/service/eventBus'

/**
 * 用于非父子组件通信
 * @type {{receive: EventBusUtils.receive , send: EventBusUtils.send}}
 */
var EventBusUtils = {

  /**
   * 发送消息
   * @param key
   * @param value
   */
  send: function (key, value) {
    EventBus.$emit(key, value);
  },
  /**
   * 接收消息
   * @param key
   * @param callback
   */
  receive: function (key, callback) {
    EventBus.$on(key, callback);
  }
}

export default EventBusUtils
