package kollchap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;

import static org.springframework.restdocs.RestDocumentation.document;
import static org.springframework.restdocs.RestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
        .apply(documentationConfiguration())
        .alwaysDo(document("{method-name}/{step}/"))
        .build();
  }

  @Test
  public void index() throws Exception {
    this.mockMvc.perform(get("/").accept(MediaTypes.HAL_JSON))
        .andExpect(status().isOk())
        .andDo(document("index",
            links(
                linkWithRel("characters").description("The <<resources-notes, Characters resource>>")),
            responseFields(
                fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources"))));
  }

  @Test
  public void getCharacter() throws Exception {
    this.mockMvc.perform(get("/").accept(MediaTypes.HAL_JSON))
        .andExpect(status().isOk());
  }

  private String getLink(MvcResult result, String rel)
      throws UnsupportedEncodingException {
    return JsonPath.parse(result.getResponse().getContentAsString()).read(
        "_links." + rel + ".href");
  }
}