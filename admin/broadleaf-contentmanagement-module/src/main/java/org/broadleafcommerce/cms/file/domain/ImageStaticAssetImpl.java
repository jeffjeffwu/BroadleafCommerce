/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.cms.file.domain;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.openadmin.audit.AdminAuditableListener;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Created by bpolster.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(value = { AdminAuditableListener.class })
@Table(name = "BLC_IMG_STATIC_ASSET")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blCMSElements")
public class ImageStaticAssetImpl extends StaticAssetImpl implements ImageStaticAsset {

    @Column(name ="WIDTH")
    @AdminPresentation(friendlyName = "ImageStaticAssetImpl_Width",
            order = Presentation.FieldOrder.LAST + 1000,
            tab = Presentation.Tab.Name.File_Details, tabOrder = Presentation.Tab.Order.File_Details,
            readOnly = true)
    protected Integer width;

    @Column(name ="HEIGHT")
    @AdminPresentation(friendlyName = "ImageStaticAssetImpl_Height",
            order = Presentation.FieldOrder.LAST + 2000,
            tab = Presentation.Tab.Name.File_Details, tabOrder = Presentation.Tab.Order.File_Details,
            readOnly = true)
    protected Integer height;

    @Override
    public Integer getWidth() {
        return width;
    }

    @Override
    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public Integer getHeight() {
        return height;
    }

    @Override
    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public ImageStaticAsset cloneEntity() {

        ImageStaticAssetImpl asset = new ImageStaticAssetImpl();
        asset.name = name;
        asset.site = site;
        asset.archivedFlag = archivedFlag;
        asset.deletedFlag = deletedFlag;
        asset.fullUrl = fullUrl;
        asset.fileSize = fileSize;
        asset.mimeType = mimeType;
        asset.sandbox = sandbox;
        asset.originalAssetId = originalAssetId;
        asset.width = width;
        asset.height = height;

        for (String key : contentMessageValues.keySet()) {
            StaticAssetDescription oldAssetDescription = contentMessageValues.get(key);
            StaticAssetDescription newAssetDescription = oldAssetDescription.cloneEntity();
            asset.getContentMessageValues().put(key, newAssetDescription);
        }

        return asset;
    }
}
