package ru.netology.book.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe INNER JOIN category  ON category.category_id = category")
    fun getAll(): LiveData<List<RecipeAndCategory>>

    @Query("SELECT * FROM recipe WHERE liked = 1 ORDER BY id DESC")
    fun getAllLike(): LiveData<List<RecipeEntity>>


    @Insert
    fun insert(recipe: RecipeEntity)


    @Query("UPDATE recipe SET name = :name, author = :author, content = :content, category = :category WHERE id = :id")
    fun updateContentById(id: Long, name: String, author: String, content: String, category: Int)


    @Query("DELETE FROM recipe WHERE id = :id")
    fun removeById(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(recipe: List<RecipeEntity>)



    @Query("SELECT COUNT(name) FROM recipe")
    fun rowCount(): Int


    @Query("UPDATE recipe SET liked =:isLiked WHERE id = :id")
    fun updateLike(id: Long, isLiked: Boolean)

    @Query("UPDATE category SET is_visible = :visibility  WHERE category_id = :id")
    fun updateCategoryVisibility(id: Int, visibility: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCategory(category: List<CategoryEntity>)

    @Query("SELECT * FROM category")
    fun categories(): LiveData<List<CategoryEntity>>
}