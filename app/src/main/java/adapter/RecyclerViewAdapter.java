package adapter;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignment_3.databinding.RecycleViewBinding;

import java.util.List;

import entity.PainEntryData;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder> {

    private List<PainEntryData> painEntryDataList;
    public RecyclerViewAdapter(List<PainEntryData> painEntryDataList) {
        this.painEntryDataList = painEntryDataList;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                                     parent, int viewType) {
        RecycleViewBinding binding =
                RecycleViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder
                                         viewHolder, int position) {

        final PainEntryData set = painEntryDataList.get(position);
        viewHolder.binding.uidPain.setText(String.valueOf(set.getUid()));
        viewHolder.binding.rvPainLoc.setText(set.getPainLocation());
        viewHolder.binding.painLevel.setText((set.getPainIntensityLevel()));
        viewHolder.binding.moodLevel.setText((set.getMoodLevel()));
        viewHolder.binding.stepTaken.setText((set.getStepTaken()));
        viewHolder.binding.date.setText((set.getDate()));
        viewHolder.binding.temperature.setText((set.getTemperature()));
        viewHolder.binding.humidity.setText((set.getHumidity()));
        viewHolder.binding.pressure.setText((set.getPressure()));
        viewHolder.binding.userEmail.setText((set.getUserEmail()));

    }

    @Override
    public int getItemCount() {
        return painEntryDataList.size();
    }

    public void addUnits(List<PainEntryData> painEntry) {
        painEntryDataList = painEntry;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecycleViewBinding binding;
        public ViewHolder(RecycleViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

