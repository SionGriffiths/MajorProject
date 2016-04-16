$$@Test
public void testShowPlants() throws Exception {
    String testBarCode = "bc1";
    this.mockMvc.perform(get("/plants").sessionAttrs(sessionattr))
              .andDo(print())
              .andExpect(status().isOk())
              .andExpect(content().string(containsString(testBarCode)))
              .andExpect(view().name("plants/show"));
}