package org.summarly;

import org.junit.Test;
import org.summarly.common.TextReader;

/**
 * Created by Anton Chernetskij
 */
public class ApplicationPipelineTest {
    @Test
    public void testProcess() throws Exception {

        TextReader reader = new TextReader();
        String s = reader.readText("src/test/resources/Hoverberget.txt");

        ApplicationPipeline pipeline = new ApplicationPipeline();
        pipeline.process(s, "Hoverberget");
    }
}
