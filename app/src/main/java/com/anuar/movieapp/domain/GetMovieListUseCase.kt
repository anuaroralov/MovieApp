package com.anuar.movieapp.domain

class GetMovieListUseCase(private val repository: Repository) {

     suspend fun run(listId:Int,page:Int):List<Movie> {
        return repository.getMovieList(listId,page)
    }
}