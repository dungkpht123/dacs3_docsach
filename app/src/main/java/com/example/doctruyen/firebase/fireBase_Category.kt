package com.example.doctruyen.firebase

class fireBase_Category {
    var id: String = ""
    var category: String = ""
    var timestamp: Long = 0
    var uid: String = ""

    constructor()

    constructor(id: String,  category: String, timestamp: Long, uid: String) {
        this.id = id
        this.category = category
        this.timestamp = timestamp
        this.uid = uid
    }


}
data class Test(    var id:String?=null,
                    var category:String?=null,
                    var timestamp:Long?=null,
                    var uid:String?=null
)