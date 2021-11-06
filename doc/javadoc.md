# Adding Javadoc to generated files

Silverchain offers a feature that copies Javadoc comments from action classes. ([What are action classes?](./tutorial.md#implement-actions))

To generate commented fluent APIs, you need to run Silverchain _twice_. Specifically,

1. create an AG file and run Silverchain to obtain action interfaces (e.g. `silverchain --input melodychain.ag`),

2. create action classes and add comments to the methods in those classes (by hand), and

3. run Silverchain again with `--javadoc <path>`. (e.g. `silverchain --input melodychain.ag --javadoc src/main/java` )

The Java files generated in Step (1) have no Javadoc comments. On the other hand, the files generated in Step (3) have Javadoc comments copied from the action classes created in Step (2).
