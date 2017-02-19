package dm.com.silentmusicparty;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectableAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>
{
    @SuppressWarnings("unused")
    private static final String TAG = SelectableAdapter.class.getSimpleName();

    private SparseBooleanArray selectedItems;

    SelectableAdapter()
    {
        selectedItems = new SparseBooleanArray();
    }

    public void updateItemAt(int position, boolean isSelected)
    {
        if (selectedItems != null)
        {
            selectedItems.put(position, isSelected);
        }
    }

    /**
     * Indicates if the item at position position is selected
     *
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    public boolean isSelected(int position)
    {
        return getSelectedItems().contains(position);
    }

    /**
     * Toggle the selection status of the item at a given position
     *
     * @param position Position of the item to toggle the selection status for
     */
    public void toggleSelection(int position)
    {
        if (selectedItems.get(position, false))
        {
            selectedItems.delete(position);
        }
        else
        {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void clearSelection(int position)
    {
        if (selectedItems.get(position, false))
        {
            selectedItems.delete(position);
        }
        else
        {
            selectedItems.put(position, true);
        }
    }

    /**
     * Clear the selection status for all items
     */
    public void clearSelection()
    {
        List<Integer> selection = getSelectedItems();
        selectedItems.clear();
        for (Integer i : selection)
        {
            notifyItemChanged(i);
        }
    }

    private void selectAll(int itemsCount)
    {
        for (int i = 0; i < itemsCount; i++)
        {
            selectedItems.put(i, true);
            notifyItemChanged(i);
        }
    }

    public void deSelectAll(int itemsCount)
    {
        selectedItems.clear();
        for (int i = 0; i < itemsCount; i++)
        {
            notifyItemChanged(i);
        }
    }

    public void toggleSelectAll(int itemsCount)
    {
        if (selectedItems.size() == itemsCount)
        {
            deSelectAll(itemsCount);
        }
        else
        {
            selectAll(itemsCount);
        }
    }

    /**
     * Count the selected items
     *
     * @return Selected items count
     */
    public int getSelectedItemCount()
    {
        return getSelectedItems().size();
    }

    /**
     * Indicates the list of selected items
     *
     * @return List of selected items ids
     */
    public List<Integer> getSelectedItems()
    {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i)
        {
            if (selectedItems.valueAt(i))
            {
                items.add(selectedItems.keyAt(i));
            }
        }
        return items;
    }
}