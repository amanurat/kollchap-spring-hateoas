package kollchap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.rest.webmvc.BaseUri;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KollchapApplication.class)
@WebAppConfiguration
public class KollchapApplicationTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private BaseUri baseUri;

  private MockMvc mockMvc;


  @Rule
  public final RestDocumentation restDocumentation = new RestDocumentation("build/generated-snippets");


  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
        .apply(documentationConfiguration(restDocumentation))
        .alwaysDo(document("{method-name}/{step}/"))
        .build();
  }

  @Test
  public void indexExample() throws Exception {
    this.mockMvc.perform(get("/").accept(MediaTypes.HAL_JSON))
        .andExpect(status().isOk())
        .andDo(document("index-example",
            links(
                linkWithRel("characters").description("The <<resources-notes, Characters resource>>"),
                linkWithRel("profile").description("The <<resources-notes, alps resource>>")),
            responseFields(
                fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources"))));
  }

  @Test
  public void charactersGetExample() throws Exception {
    this.mockMvc.perform(get("/characters").accept(MediaTypes.HAL_JSON))
        .andExpect(status().isOk())
        .andDo(document("characters-get-example"));
  }

  @Test
  public void characterGetExample() throws Exception {
    this.mockMvc.perform(get("/characters/1").accept(MediaTypes.HAL_JSON))
        .andExpect(status().isOk())
        .andDo(document("character-get-example",
            responseFields(
                fieldWithPath("name").description("Full name of character"),
                fieldWithPath("background").description("Background history and motivation"),
                fieldWithPath("_links").description("<<resources-links,Links>> to other resources"))));
  }


  @Test
  public void characterPostExample() throws Exception {

    String bobert = objectMapper.writeValueAsString(new GameCharacter("Bobert", "Merchant in the dungeon"));

    this.mockMvc.perform(post("/characters").accept(MediaTypes.HAL_JSON).content(bobert))
        .andExpect(status().isCreated())
        .andDo(document("character-create-example",
            requestFields(
                fieldWithPath("name").description("Full name of character"),
                fieldWithPath("background").description("Background history and motivation"))))
        .andReturn().getResponse()
        .getHeader("Location");

  }


  private String getLink(MvcResult result, String rel)
      throws UnsupportedEncodingException {
    return JsonPath.parse(result.getResponse().getContentAsString()).read(
        "_links." + rel + ".href");
  }
}