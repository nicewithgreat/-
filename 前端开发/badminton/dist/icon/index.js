Component({
    externalClasses: ["l-class", "l-class-self", "l-self-class"],
    options: {
        addGlobalClass: !0
    },
    properties: {
        name: String,
        color: String,
        size: String
    },
    data: {
        default: {
            size: 40,
            color: "#45526B"
        }
    },
    ready: function () {
        this.data.name || console.error("请传入Icon组件的name属性")
    },
    methods: {}
});