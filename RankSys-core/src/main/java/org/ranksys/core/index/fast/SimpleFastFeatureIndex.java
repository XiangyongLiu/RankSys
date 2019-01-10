/* 
 * Copyright (C) 2015 Information Retrieval Group at Universidad Autónoma
 * de Madrid, http://ir.ii.uam.es
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.ranksys.core.index.fast;

import java.util.stream.Stream;
import org.ranksys.core.util.IdxIndex;

/**
 * Simple implementation of FastFeatureIndex backed by a bi-map IdxIndex
 *
 * @author Saúl Vargas (saul.vargas@uam.es)
 *
 * @param <F> type of the features
 */
public class SimpleFastFeatureIndex<F> implements FastFeatureIndex<F> {

    private final IdxIndex<F> fMap;

    /**
     * Constructor.
     *
     */
    protected SimpleFastFeatureIndex() {
        this.fMap = new IdxIndex<>();
    }

    @Override
    public boolean containsFeature(F f) {
        return fMap.containsId(f);
    }

    @Override
    public int numFeatures() {
        return fMap.size();
    }

    @Override
    public Stream<F> getAllFeatures() {
        return fMap.getIds();
    }

    @Override
    public int feature2fidx(F f) {
        return fMap.get(f);
    }

    @Override
    public F fidx2feature(int fidx) {
        return fMap.get(fidx);
    }

    /**
     * Add a new feature to the index. If the feature already exists, nothing is done.
     *
     * @param f id of the feature
     * @return index of the feature
     */
    protected int add(F f) {
        return fMap.add(f);
    }
    
    /**
     * Removes a item from the index. If the item does not exist, nothing is done
     * @param i id of the item
     * @return old index of the item, -1 if it was not in the index.
     */
    protected int remove(F i) {
        return fMap.remove(i);
    }

    /**
     * Creates a feature index from a stream of feature objects.
     *
     * @param <F> type of the features
     * @param features stream of feature objects
     * @return a fast feature index
     */
    public static <F> SimpleFastFeatureIndex<F> load(Stream<F> features) {
        SimpleFastFeatureIndex<F> featureIndex = new SimpleFastFeatureIndex<>();
        features.forEach(featureIndex::add);
        return featureIndex;
    }

}
