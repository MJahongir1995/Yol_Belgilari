package uz.jahongir.yol_belgilari.db

import uz.jahongir.yol_belgilari.models.MyData

interface MyInterface {
    fun addSign(myData: MyData)
    fun getAllSign():List<MyData>
    fun deleteSign(myData: MyData)
    fun editSign(myData: MyData)
}