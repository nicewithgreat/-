//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    interval: null,
    text: '      每日订场于中午12:00开始，场地时间段为17:30-19：30以及19:30-22:00,请根据具体情况订场，每日每人限订一个场地，谢谢支持',
    pace: 1.2, //滚动速度
    interval: 20, //时间间隔
    size: 15, //字体大小in px
    length: 0, //字体宽度
    offsetLeft: 0, //
    windowWidth: 0,
  },
  //根据viewId查询view的宽度
  queryViewWidth: function(viewId) {
    //创建节点选择器
    return new Promise(function(resolve) {
      var query = wx.createSelectorQuery();
      var that = this;
      query.select('.' + viewId).boundingClientRect(function(rect) {
        resolve(rect.width);
      }).exec();
    });

  },
  //停止跑马灯
  stopMarquee: function() {
    var that = this;
    //清除旧的定时器
    if (that.data != null) {
      clearInterval(that.interval);
    }
  },
  //执行跑马灯动画
  excuseAnimation: function() {
    var that = this;
    if (that.data.length > that.data.windowWidth) {
      //设置循环
      let interval = setInterval(function() {
        if (that.data.offsetLeft <= 0) {
          if (that.data.offsetLeft >= -that.data.length) {
            that.setData({
              offsetLeft: that.data.offsetLeft - that.data.pace,
            })
          } else {
            that.setData({
              offsetLeft: that.data.windowWidth,
            })
          }
        } else {
          that.setData({
            offsetLeft: that.data.offsetLeft - that.data.pace,
          })
        }
      }, that.data.interval);
    }
  },
  //开始跑马灯
  startMarquee: function() {
    var that = this;
    that.stopMarquee();
    //初始化数据
    that.data.windowWidth = wx.getSystemInfoSync().windowWidth;
    that.queryViewWidth('text').then(function(resolve) {
      that.data.length = resolve;
      console.log(that.data.length + "/" + that.data.windowWidth);
      that.excuseAnimation();
    });
  },
  onShow: function() {
    this.startMarquee();
  },
})
