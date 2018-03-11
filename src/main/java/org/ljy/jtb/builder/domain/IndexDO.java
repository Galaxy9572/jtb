package org.ljy.jtb.builder.domain;

import lombok.Data;
import org.ljy.jtb.constants.IndexMethod;
import org.ljy.jtb.constants.IndexType;

/**
 * @author ljy
 */
@Data
public class IndexDO {

    private String indexName;

    private String indexType;

    private String method;


    public static final class Builder {
        private String indexName;
        private String indexType;
        private String method;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder indexName(String indexName) {
            this.indexName = indexName;
            return this;
        }

        public Builder indexType(String indexType) {
            this.indexType = indexType;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public IndexDO build() {
            IndexDO indexDO = new IndexDO();
            indexDO.setIndexName(indexName);
            indexDO.setIndexType(indexType);
            indexDO.setMethod(method);
            return indexDO;
        }
    }
}
