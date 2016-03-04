package com.example.Kamini.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "itemPhotoApi",
        version = "v1",
        resource = "itemPhoto",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Kamini.example.com",
                ownerName = "backend.myapplication.Kamini.example.com",
                packagePath = ""
        )
)
public class ItemPhotoEndpoint {

    private static final Logger logger = Logger.getLogger(ItemPhotoEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(ItemPhoto.class);
    }

    /**
     * Returns the {@link ItemPhoto} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code ItemPhoto} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "itemPhoto/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public ItemPhoto get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting ItemPhoto with ID: " + id);
        ItemPhoto itemPhoto = ofy().load().type(ItemPhoto.class).id(id).now();
        if (itemPhoto == null) {
            throw new NotFoundException("Could not find ItemPhoto with ID: " + id);
        }
        return itemPhoto;
    }

    /**
     * Inserts a new {@code ItemPhoto}.
     */
    @ApiMethod(
            name = "insert",
            path = "itemPhoto",
            httpMethod = ApiMethod.HttpMethod.POST)
    public ItemPhoto insert(ItemPhoto itemPhoto) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that itemPhoto.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(itemPhoto).now();
        logger.info("Created ItemPhoto with ID: " + itemPhoto.getId());

        return ofy().load().entity(itemPhoto).now();
    }

    /**
     * Updates an existing {@code ItemPhoto}.
     *
     * @param id        the ID of the entity to be updated
     * @param itemPhoto the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code ItemPhoto}
     */
    @ApiMethod(
            name = "update",
            path = "itemPhoto/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public ItemPhoto update(@Named("id") Long id, ItemPhoto itemPhoto) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(itemPhoto).now();
        logger.info("Updated ItemPhoto: " + itemPhoto);
        return ofy().load().entity(itemPhoto).now();
    }

    /**
     * Deletes the specified {@code ItemPhoto}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code ItemPhoto}
     */
    @ApiMethod(
            name = "remove",
            path = "itemPhoto/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(ItemPhoto.class).id(id).now();
        logger.info("Deleted ItemPhoto with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "itemPhoto",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<ItemPhoto> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<ItemPhoto> query = ofy().load().type(ItemPhoto.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<ItemPhoto> queryIterator = query.iterator();
        List<ItemPhoto> itemPhotoList = new ArrayList<ItemPhoto>(limit);
        while (queryIterator.hasNext()) {
            itemPhotoList.add(queryIterator.next());
        }
        return CollectionResponse.<ItemPhoto>builder().setItems(itemPhotoList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(ItemPhoto.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find ItemPhoto with ID: " + id);
        }
    }
}