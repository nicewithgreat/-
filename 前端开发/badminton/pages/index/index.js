//index.js
//获取应用实例
const app = getApp()

Page({
    showDialog() {
        wx.lin.showDialog({
            type: "confirm",
            title: "预定场",
            content: "您确定要预定这个场地吗？",
            success: (res) => {
                if (res.confirm) {
                    //调用后台函数把信息写入数据库
                    console.log('用户点击了确定')
                } else if (res.cancel) {
                    console.log('用户点击了取消')
                }
            }
        })
    }
})