package com.cdac.iaf.bookmycoolie.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.FaqModel;

import java.util.ArrayList;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {

    private ArrayList<FaqModel> faqList;
    private Context context;

    public FaqAdapter(Context context, ArrayList<FaqModel> faqList) {
        this.faqList = faqList;
        this.context = context;
    }

    @NonNull
    @Override
    public FaqAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faq_layout, parent, false);
        return new FaqAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqAdapter.ViewHolder holder, int position) {
        FaqModel faqModel = faqList.get(position);
        System.out.println(faqModel);
        holder.serialNo.setText(String.valueOf(faqModel.getFaqId()));
        holder.questionText.setText(faqModel.getQuestion());
        holder.answerText.setText(faqModel.getAnswer());

        if(faqModel.isExpanded()) {
            holder.arrowUp.setVisibility(View.VISIBLE);
            holder.arrowDown.setVisibility(View.GONE);
            holder.answerCard.setVisibility(View.VISIBLE);
        }else {
            holder.arrowDown.setVisibility(View.VISIBLE);
            holder.arrowUp.setVisibility(View.GONE);
            holder.answerCard.setVisibility(View.GONE);
        }

        holder.questionLayout.setOnClickListener(v -> {
            System.out.println("isExpanded() "+faqModel.isExpanded());
            if(faqModel.isExpanded()) {
                faqModel.setExpanded(false);
                notifyItemChanged(position);
                holder.answerCard.setVisibility(View.GONE);
            }else {
                faqModel.setExpanded(true);
                notifyItemChanged(position);
                holder.answerCard.setVisibility(View.VISIBLE);
            }
        });

        /*holder.arrowUp.setOnClickListener(v -> {
            faqModel.setExpanded(false);
            notifyItemChanged(position);
            holder.answerCard.setVisibility(View.GONE);
        });

        holder.arrowDown.setOnClickListener(v -> {
            faqModel.setExpanded(true);
            notifyItemChanged(position);
            holder.answerCard.setVisibility(View.VISIBLE);
        });*/

    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout questionLayout;
        private TextView serialNo, questionText, answerText;
        private CardView answerCard;
        private ImageView arrowUp, arrowDown;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionLayout = itemView.findViewById(R.id.question_layout);
            questionText = itemView.findViewById(R.id.question_text);
            answerText = itemView.findViewById(R.id.answer_text);
            serialNo = itemView.findViewById(R.id.serial_number);
            answerCard = itemView.findViewById(R.id.answer_card);
            arrowUp = itemView.findViewById(R.id.arrow_up);
            arrowDown = itemView.findViewById(R.id.arrow_down);

            arrowUp.setVisibility(View.GONE);
            arrowDown.setVisibility(View.VISIBLE);
        }


    }

}
