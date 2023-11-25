package com.example.gestioncontactsfinal.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestioncontactsfinal.Model.Contact;
import com.example.gestioncontactsfinal.R;
import com.example.gestioncontactsfinal.UpdateContact;
import com.example.gestioncontactsfinal.dao.DAOContact;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ContactViewHolder> {
    private List<Contact> contactList;
    private Context context;
    private OnItemClickListener itemClickListener;


    public interface OnItemClickListener {
        void onItemClick(String email);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public Adapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Contact contact = contactList.get(position);

        holder.textNom.setText(contact.getNom());
        holder.textTel.setText(contact.getTel());

        if (contact.isFavorite()) {
            holder.fav.setImageResource(R.drawable.favorit);
        } else {
            holder.fav.setImageResource(R.drawable.favorit_border);
        }






        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(contact.getEmail());
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int deletePosition = holder.getAdapterPosition();

                Contact ContactPosition = contactList.get(deletePosition);

                contactList.remove(deletePosition);

                notifyItemRemoved(deletePosition);

                DAOContact.deleteContact(ContactPosition);
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateContact.class);

                intent.putExtra("nom", contact.getNom());
                intent.putExtra("prenom", contact.getPrenom());
                intent.putExtra("email", contact.getEmail());
                intent.putExtra("service", contact.getService());
                intent.putExtra("tel", contact.getTel());
                intent.putExtra("fav", contact.isFavorite());

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public ImageView call;
        public ImageView deleteButton;
        public ImageView editButton;
        public ImageView fav;
        public TextView textNom;
        public TextView textTel;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textNom = itemView.findViewById(R.id.textNom);
            textTel = itemView.findViewById(R.id.textTel);
            deleteButton = itemView.findViewById(R.id.btndel);
            editButton = itemView.findViewById(R.id.btnedit);
            fav = itemView.findViewById(R.id.favorit);
            call = itemView.findViewById(R.id.call);

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Contact contact = contactList.get(getAdapterPosition());
                    boolean isFavorite = contact.isFavorite();
                    boolean newFavoriteState = !isFavorite;
                    contact.setFavorite(newFavoriteState);

                    if (newFavoriteState) {
                        fav.setImageResource(R.drawable.favorit);
                    } else {
                        fav.setImageResource(R.drawable.favorit_border);
                    }
                    DAOContact.updateFavoriteState(contact.getEmail(), newFavoriteState);
                }
            });








        }
    }
}

