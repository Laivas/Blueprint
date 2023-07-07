package scrapperUtil;

import java.io.File;
import java.io.IOException;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;


import com.sun.codemodel.JCodeModel;

public class JsonToPojoGenerator {
	
	
	
	public void convertJsonToJavaClass(String inputJson, File outputJavaClassDirectory, String javaClassName,
			String packageName) {

		JCodeModel jcodeModel = new JCodeModel();
		
		GenerationConfig config = new DefaultGenerationConfig() {

			@Override
			public boolean isGenerateBuilders() {
				return false;
			}

			@Override
			public SourceType getSourceType() {
				return SourceType.JSON;
			}

		};
		
	
		SchemaMapper mapper = new SchemaMapper(
				new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
		
	

		try {
			
			mapper.generate(jcodeModel, javaClassName, packageName, inputJson);
			
			jcodeModel.build(outputJavaClassDirectory);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
