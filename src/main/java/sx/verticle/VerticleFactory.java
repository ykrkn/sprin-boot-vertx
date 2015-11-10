package sx.verticle;

import org.springframework.stereotype.Component;
import sx.IConnector;
import sx.verticle.impl.*;

/**
 * Created by sx on 08.11.15.
 */
@Component
public class VerticleFactory implements IVerticleFactory {

    @Override
    public QueryProducerVerticle createQueryProducer(String sourceUrl) {
        return new QueryProducerVerticle(sourceUrl);
    }

    @Override
    public ExtractorVerticle createExtractor(IConnector connector) {
        return new ExtractorVerticle(connector);
    }

    @Override
    public TransformerVerticle createTransformer(String sourceUrl) {
        return new TransformerVerticle(sourceUrl);
    }

//    @Override
//    public ProcessorVerticle createProcessor(String sourceUrl) {
//        return new ProcessorVerticle(sourceUrl);
//    }
//
//    @Override
//    public MapperVerticle createMapper(String sourceUrl, String datasetUrl) {
//        return new MapperVerticle(sourceUrl, datasetUrl);
//    }
//
//    @Override
//    public ResolverVerticle createLocationResolver(String datasetUrl) {
//        return new ResolverVerticle(datasetUrl);
//    }
//
//    @Override
//    public ResolverVerticle createPeriodResolver(String datasetUrl) {
//        return new ResolverVerticle(datasetUrl);
//    }
//
//    @Override
//    public ResolverVerticle createMetricResolver(String datasetUrl) {
//        return new ResolverVerticle(datasetUrl);
//    }
//
//    @Override
//    public LoaderVerticle createLoader(IConnector connector) {
//        return new LoaderVerticle(connector);
//    }
}
