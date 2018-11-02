package io.borlandfcsd.cinemasystem.controller;

import io.borlandfcsd.cinemasystem.entity.dto.MovieDto;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import io.borlandfcsd.cinemasystem.service.MovieService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import javax.servlet.Filter;

import java.io.File;
import java.io.FileInputStream;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringJUnitWebConfig(locations = {"file:src/main/webapp/WEB-INF/cinema-servlet.xml"})
public class MovieControllerTest {

    private MockMvc mockMvc;

    @Autowired
    Filter springSecurityFilterChain;

    @Mock
    private MovieService movieService;

    @BeforeEach
    public void setUp(WebApplicationContext wac) throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void givenWebAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(mockMvc);
    }

    @Test
    @WithUserDetails(value = "example@mail.com")
    public void getMoviesPageShouldReturnForbiddenCode() throws Exception {
        mockMvc.perform(get("/admin/movies").with(csrf()))
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails(value = "borlandfcsd@gmail.com")
    public void getMoviesPageShouldReturnOkCode() throws Exception {
        mockMvc.perform(get("/admin/movies").with(csrf()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("example@mail.com")
    public void addMovieShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/admin/save"))
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("borlandfcsd@gmail.com")
    public void addMovieWithValidDataShouldReturnOk() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Batman");
        movie.setDuration(90);

        MovieDto dto = new MovieDto();
        dto.setMovie(movie);


        File file = new File("D:\\posters\\fight-club.jpg");
        FileInputStream fi = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("poster", file.getName(), "multipart/form-data", fi);

        dto.setPoster(multipartFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/save")
                .file(multipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .flashAttr("movieDto",dto)
                .with(csrf().asHeader()))
                .andExpect(status().is(302))
                .andExpect(flash().attribute("message","has been added"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @WithUserDetails("borlandfcsd@gmail.com")
    public void addMovieWithInvalidDataShouldReturnError() throws Exception {
        Movie movie = new Movie();
        movie.setDuration(90);

        MovieDto dto = new MovieDto();
        dto.setMovie(movie);


        File file = new File("D:\\posters\\fight-club.jpg");
        FileInputStream fi = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("poster", file.getName(), "multipart/form-data", fi);

        dto.setPoster(multipartFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/save")
                .file(multipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .flashAttr("movieDto",dto)
                .with(csrf().asHeader()))
                .andExpect(status().is(302))
                .andExpect(flash().attribute("exception","didn't create.Fill in the required fields, please"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @WithUserDetails("example@mail.com")
    public void removeMovieShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/admin/removeMovie/3"))
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
     @WithUserDetails("borlandfcsd@gmail.com")
    public void removeMovieShouldReturnOk() throws Exception {
        mockMvc.perform(get("/admin/removeMovie/3"))
                .andExpect(status().is(302))
                .andExpect(header().string("Location", is("/admin/movies")))
                .andExpect(flash().attribute("message","has been removed"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void moviePage() throws Exception {
        mockMvc.perform(get("/moviePage/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attribute("movie", hasProperty("title", is("Deadpool"))))
                .andDo(MockMvcResultHandlers.print());
    }

}
   /*



    @Test
    @WithUserDetails("example@mail.com")
    public void editMovie() {
    }



}*/