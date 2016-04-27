$$@Value("${image-repository.root.static-link}")
private String imageRepoRoot;

/**
* Adds a resource handler to allow definition of resource locations outside of the spring default
* for serving static content
* @param registry The ResourceHandlerRegistry storing resource handler information
*/
$$@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String myExternalFilePath = imageRepoRoot;

    registry.addResourceHandler("/images/**")
            .addResourceLocations(myExternalFilePath);

    super.addResourceHandlers(registry);
}