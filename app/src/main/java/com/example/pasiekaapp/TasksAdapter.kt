package com.example.pasiekaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter(private val tasksList: MutableList<String>) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasksList[position]
        holder.tvTask.text = task
        holder.cbTaskCompleted.setOnCheckedChangeListener { _, isChecked ->

        }
        holder.btnDeleteTask.setOnClickListener {
            tasksList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, tasksList.size)
        }
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTask: TextView = itemView.findViewById(R.id.tvTask)
        val cbTaskCompleted: CheckBox = itemView.findViewById(R.id.cbTaskCompleted)
        val btnDeleteTask: ImageButton = itemView.findViewById(R.id.btnDeleteTask)
    }


}
