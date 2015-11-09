package com.jpuyo.barcelonaplaces.app.ui.activity.main;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jpuyo.barcelonaplaces.app.R;
import com.jpuyo.barcelonaplaces.app.database.managers.PlaceDetailManager;
import com.jpuyo.barcelonaplaces.app.ui.components.NotNullableTextView;

import java.util.HashMap;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardCursorAdapter;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * This class is an adapter for show the place details
 * on a card view.
 * We have a static inner class that is the viewholder,
 * where we setup the details.
 * We can expand the card. When user click on expand button,
 * card expand and shows the long description of the place.
 *
 * We use the cardlib library:
 * https://github.com/gabrielemariotti/cardslib
 *
 */
public class PlacesCursorCardAdapter extends CardCursorAdapter {

    private static PlaceDetailManager placeDetailManager;
    private static View.OnClickListener onClickListener;

    public PlacesCursorCardAdapter(Context context, View.OnClickListener onClickListener) {
        super(context);
        PlacesCursorCardAdapter.onClickListener = onClickListener;
    }

    public void setPlaceDetailManager(PlaceDetailManager placeDetailManager) {
        PlacesCursorCardAdapter.placeDetailManager = placeDetailManager;
    }

    /**
     * Returns a card with:
     * -Place header
     * -Place details
     * -Place expand area with the long description of the place
     * @param cursor Cursor that contains place data
     * @return
     */
    @Override
    protected Card getCardFromCursor(Cursor cursor) {
        PlaceDetailsViewHolder card = new PlaceDetailsViewHolder(super.getContext());
        setCardFromCursor(card, cursor);

        CardHeader header = new CardHeader(mContext);
        header.setTitle(card.title);
        header.setButtonExpandVisible(true);
        card.addCardHeader(header);

        return card;
    }

    /**
     * Sets on the card all attributes of the place details
     * and create the expand area
     * @param card Cursor that contains place details
     * @param cursor
     */
    private void setCardFromCursor(PlaceDetailsViewHolder card, Cursor cursor) {

        card.setId(String.valueOf(placeDetailManager.getPlaceRowId(cursor)));
        card.title = placeDetailManager.getPlaceTitle(cursor);
        card.address = placeDetailManager.getPlaceAddress(cursor);
        card.transport = placeDetailManager.getPlaceTransport(cursor);
        card.email = placeDetailManager.getPlaceEmail(cursor);
        card.phone = placeDetailManager.getPlacePhone(cursor);
        card.fax = placeDetailManager.getPlaceFax(cursor);
        card.description = placeDetailManager.getPlaceDescription(cursor);
        card.geocoordinates = placeDetailManager.getGeoCoordinates(cursor);

        CardExpand expand = new CardExpand(mContext);
        expand.setTitle(card.description);
        card.addCardExpand(expand);
    }

    /**
     * View Holder for the Place Details Card
     */
    private static class PlaceDetailsViewHolder extends Card {

        String title;
        String address;
        String transport;
        String email;
        String phone;
        String fax;
        String description;
        String geocoordinates;

        private final static HashMap<String,Integer> imageResources = new HashMap<>();
        static {
            imageResources.put("underground",R.drawable.ic_underground);
            imageResources.put("L1",R.drawable.ic_l1);
            imageResources.put("L2",R.drawable.ic_l2);
            imageResources.put("L3",R.drawable.ic_l3);
            imageResources.put("L4",R.drawable.ic_l4);
            imageResources.put("L5",R.drawable.ic_l5);
        }

        public PlaceDetailsViewHolder(Context context) {
            super(context, R.layout.placeslist_fragment_card_expandable);
        }

        /**
         * We configure all the elements of the place details
         * @param parent
         * @param view
         */
        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            setupTextViews(parent);
            setupTransportLayout(parent);
            setupImageButtons(parent);
        }

        /**
         * Setup the TextViews.
         * @param parent
         */
        private void setupTextViews(ViewGroup parent) {
            setTextOnTextView(parent, R.id.card_place_address, R.string.label_empty, address);
            setTextOnTextView(parent, R.id.card_place_email, R.string.label_empty, email);
            setTextOnTextView(parent, R.id.card_place_phone, R.string.label_phone, phone);
            setTextOnTextView(parent, R.id.card_place_fax, R.string.label_fax, fax);
        }

        /**
         * Setup the transport Layout: <transportTypeIcon> <transportStation> <transportLineIcon>
         * @param parent
         */
        private void setupTransportLayout(ViewGroup parent) {

            if(transport != null){
                ImageView transportTypeIcon = (ImageView) parent.findViewById(R.id.card_place_transport_icon);
                NotNullableTextView textViewStation = (NotNullableTextView) parent.findViewById(R.id.card_place_transport_station);
                ImageView transportLineIcon = (ImageView) parent.findViewById(R.id.card_place_transport_line_icon);
                String[] transportInfo = transport.split(":");
                String transportType = transportInfo[0];
                String station = transportInfo[1];
                String transportLine = transportInfo[2];
                transportTypeIcon.setImageResource(imageResources.get(transportType));
                textViewStation.setText(station);
                transportLineIcon.setImageResource(imageResources.get(transportLine));
            }
        }

        /**
         * Setup the listeners of the image buttons
         * @param parent
         */
        private void setupImageButtons(ViewGroup parent) {
            setOnClickListenerOnImageButton(parent, R.id.action_maps,title+";"+geocoordinates);
            setOnClickListenerOnImageButton(parent, R.id.action_phone,phone);
            setOnClickListenerOnImageButton(parent, R.id.action_email, email);
        }

        /**
         * We only show the textView if value is not null
         * @param parent
         * @param textViewId
         * @param label
         * @param value
         */
        private void setTextOnTextView(ViewGroup parent, int textViewId, int label, String value) {
            NotNullableTextView textView = (NotNullableTextView) parent.findViewById(textViewId);
            if (textView != null) {
                textView.setLabelAndValue(mContext.getResources().getString(label), value);
            }
        }

        /**
         * Set onclick listener on button with <buttonId>. We need diferent params for each intent.
         * We use <paramForIntent> to receive the param (phone, email, coordinates, etc)
         * @param parent
         * @param buttonId
         * @param paramForIntent
         */
        private void setOnClickListenerOnImageButton(ViewGroup parent, int buttonId, String paramForIntent) {
            ImageButton imageButton = (ImageButton) parent.findViewById(buttonId);
            imageButton.setOnClickListener(onClickListener);
            imageButton.setTag(paramForIntent);
        }
    }
}


