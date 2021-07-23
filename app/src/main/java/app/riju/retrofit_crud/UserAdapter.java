package app.riju.retrofit_crud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.riju.retrofit_crud.ModelResponse.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHHolder> {

    Context context;
    List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;

    }

    @NonNull
    @Override
    public ViewHHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new ViewHHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHHolder holder, int position) {
        holder.userName.setText(userList.get(position).getUsername());
        holder.userEmail.setText(userList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHHolder extends RecyclerView.ViewHolder {
        TextView userName, userEmail;

        public ViewHHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.showName);
            userEmail = itemView.findViewById(R.id.showEmail);
        }
    }
}
