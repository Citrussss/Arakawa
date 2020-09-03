package top.arakawa.demo.net.bean

data class WanAndroidBaseBean<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
) {

}