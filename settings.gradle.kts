rootProject.name = "blog-search"

include("outbound", "domain", "server")

rootProject.children.forEach { project ->
    project.projectDir = file("modules/${project.name}")
    assert(project.projectDir.isDirectory)
}
