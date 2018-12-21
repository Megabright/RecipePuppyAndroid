package com.mundaco.recipepuppy.domain
import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.data.model.RecipeRequest
import com.mundaco.recipepuppy.domain.recipelist.RecipeListInteractor
import com.mundaco.recipepuppy.domain.rules.RxImmediateSchedulerRule
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit

class RecipeListUnitTest {

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    // Helpers
    @Mock
    lateinit var recipeRepository: RecipeRepository

    private val recipeList = arrayOf(
        Recipe("This is a test title","test","test","test"),
        Recipe("This is another test title","test","test","test"),
        Recipe("This is yet another test title","test","test","test"),
        Recipe("And another test title","test","test","test"),
        Recipe("Fifth test title","test","test","test"),
        Recipe("Sixth test title","test","test","test")
    )

    private lateinit var sut: RecipeListInteractor

    // Tests
    @Before
    fun setUp() {

        sut = RecipeListInteractor(recipeRepository)


        `when`(recipeRepository.searchRecipes(sut.recipeRequest)).thenAnswer { invocation ->
            val req = invocation.getArgument<RecipeRequest>(0)
            req.results = recipeList.filter { recipe ->
                recipe.title.contains(req.query)
            }.subList((req.page - 1) * 2, req.page * 2)
            Observable.just(req)
        }
    }

    @Test
    fun loadNewQueryResults_withEmptyQuery_YieldsEmptyRecipeList() {

        sut.loadNewQueryResults("")

        assertThat(sut.recipeList.value!!.size, `is`(0))

    }

    @Test
    fun loadNewQueryResults_withNonEmptyQuery_YieldsNonEmptyRecipeList() {

        sut.loadNewQueryResults("test")

        assertThat(sut.recipeList.value!!.size, `is`(2))

    }

    @Test
    fun loadNextPageResults_withEmptyQuery_ReturnsAnEmptyRecipeList() {

        sut.loadNewQueryResults("")
        sut.loadNextPageResults()

        assertThat(sut.recipeList.value!!.size, `is`(0))
    }

    @Test
    fun loadNextPageResults_twiceWithEmptyQuery_ReturnsAnEmptyRecipeList() {

        sut.loadNewQueryResults("")
        sut.loadNextPageResults()
        sut.loadNextPageResults()

        assertThat(sut.recipeList.value!!.size, `is`(0))
    }

    @Test
    fun loadNextPageResults_withNonEmptyQuery_ReturnsFourRecipes() {

        sut.loadNewQueryResults("test")
        sut.loadNextPageResults()

        assertThat(sut.recipeList.value!!.size, `is`(4))
    }

    @Test
    fun loadNextPageResults_twiceWithNonEmptyQuery_ReturnsSixRecipes() {

        sut.loadNewQueryResults("test")
        sut.loadNextPageResults()
        sut.loadNextPageResults()

        assertThat(sut.recipeList.value!!.size, `is`(6))
    }

    @Test
    fun loadNewQueryResults_afterLoadNextPageWithNonEmptyQuery_ReturnsTwoRecipes() {

        sut.loadNewQueryResults("test")
        sut.loadNextPageResults()
        sut.loadNewQueryResults("test")

        assertThat(sut.recipeList.value!!.size, `is`(2))
    }

    // TODO: Test RequestState's
}
