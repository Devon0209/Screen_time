package com.example.screentime

class BookData {
    var title: String? = null
    var description: String? = null
    var img: String? = null
    var author: String? = null
    var pdfUrl: String? = null

    constructor() {}

    constructor(title: String?, description: String?, img: String?, author: String?, pdfUrl: String?) {
        this.title = title
        this.description = description
        this.img = img
        this.author = author
        this.pdfUrl = pdfUrl
    }
}
