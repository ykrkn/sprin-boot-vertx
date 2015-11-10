package sx.verticle;

import sx.IConnector;
import sx.verticle.impl.*;

/**
 * Created by sx on 07.11.15.
 */
public interface IVerticleFactory {

    QueryProducerVerticle createQueryProducer(String sourceUrl);

    ExtractorVerticle createExtractor(IConnector connector);

    TransformerVerticle createTransformer(String sourceUrl);

//    ProcessorVerticle createProcessor(String sourceUrl);
//
//    MapperVerticle createMapper(String sourceUrl, String datasetUrl);
//
//    ResolverVerticle createLocationResolver(String datasetUrl);
//
//    ResolverVerticle createPeriodResolver(String datasetUrl);
//
//    ResolverVerticle createMetricResolver(String datasetUrl);
//
//    LoaderVerticle createLoader(IConnector connector);
}
