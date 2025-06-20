package com.nvshink.domain.utils

import android.text.Html
import com.example.newsaggregator.data.local.news.entity.NewsEntity
import com.example.newsaggregator.data.rss.dto.CategoryDto
import com.example.newsaggregator.data.rss.dto.ItemDto
import com.example.newsaggregator.domain.category.CategoryModel
import com.example.newsaggregator.domain.news.model.NewsModel
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class NewsMapper @Inject constructor() {
    fun dtoToModel(dto: ItemDto): NewsModel = NewsModel(
        title = dto.title,
        link = dto.link,
        description = Html.fromHtml(dto.description, Html.FROM_HTML_MODE_LEGACY).toString(),
        categories = dto.categories,
        pubDate = dto.pubDate ,
        guid = dto.guid,
        contents = dto.contents,
        dcCreator = dto.dcCreator,
        dcDate =  LocalDateTime.parse(dto.dcDate, DateTimeFormatter.ISO_DATE_TIME).atZone(ZoneId.systemDefault()).toLocalDateTime()
    )

    fun entityToModel(entity: NewsEntity): NewsModel = NewsModel(
        title = entity.title,
        link = entity.link,
        description = Html.fromHtml(entity.description, Html.FROM_HTML_MODE_LEGACY).toString(),
        categories = entity.categories,
        pubDate = entity.pubDate,
        guid = entity.guid,
        contents = entity.contents,
        dcCreator = entity.dcCreator,
        dcDate = LocalDateTime.parse(entity.dcDate, DateTimeFormatter.ISO_DATE_TIME).atZone(ZoneId.systemDefault()).toLocalDateTime()
    )

    fun modelToEntity(news: NewsModel): NewsEntity = NewsEntity(
        title = news.title,
        link = news.link,
        description = news.description,
        categories = news.categories,
        pubDate = news.pubDate,
        guid = news.guid,
        contents = news.contents,
        dcCreator = news.dcCreator,
        dcDate = news.dcDate.format(DateTimeFormatter.ISO_DATE_TIME)
    )

}

class CategoriesMapper @Inject constructor() {
    fun dtoToModel(dto: CategoryDto, count: Int = 0, isSelected: Boolean = false): CategoryModel = CategoryModel(
        domain = dto.domain,
        value = dto.value,
        count = count,
        isSelected = isSelected
    )

}