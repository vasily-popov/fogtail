package com.vascome.fogtail.presentation.dev_settings.adapters

import android.annotation.SuppressLint
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.vascome.fogtail.FogtailRobolectricUnitTestRunner
import com.vascome.fogtail.R
import com.vascome.fogtail.presentation.dev_settings.adapters.DeveloperSettingsSpinnerAdapter.SelectionOption

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import java.util.ArrayList

import java.util.Arrays.asList
import java.util.Collections.emptyList
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.Mockito.`when`

@Suppress("FunctionName")
@RunWith(FogtailRobolectricUnitTestRunner::class)
class DeveloperSettingsSpinnerAdapterTest {

    lateinit private var layoutInflater: LayoutInflater
    lateinit private var adapter: DeveloperSettingsSpinnerAdapter<SelectionOption>

    @Before
    fun beforeEachTest() {
        layoutInflater = mock(LayoutInflater::class.java)
        adapter = DeveloperSettingsSpinnerAdapter(layoutInflater)
    }

    @Test
    fun setSelectableOptions_shouldReturnSameAdapter() {
        assertThat(adapter.setSelectionOptions(emptyList())).isSameAs(adapter)
    }

    @Test
    fun setSelectableOptions_shouldNotifyObservers() {
        val dataSetObserver = mock(DataSetObserver::class.java)
        adapter.registerDataSetObserver(dataSetObserver)
        verifyZeroInteractions(dataSetObserver)

        adapter.setSelectionOptions(emptyList())
        verify(dataSetObserver).onChanged()
    }

    @Test
    fun getCount_shouldReturn0ByDefault() {
        assertThat(adapter.count).isEqualTo(0)
    }

    @Test
    fun getCount_shouldReturn0FromEmptyList() {
        adapter.setSelectionOptions(emptyList())
        assertThat(adapter.count).isEqualTo(0)
    }

    @Test
    fun getCount_shouldReturnCountFromList() {
        val selectionOptions = ArrayList<SelectionOption>()
        selectionOptions.add(mock(SelectionOption::class.java))
        selectionOptions.add(mock(SelectionOption::class.java))
        selectionOptions.add(mock(SelectionOption::class.java))

        adapter.setSelectionOptions(selectionOptions)
        assertThat(adapter.count).isEqualTo(3)
    }

    @Test
    fun getItem_shouldReturnItemsFromList() {
        val selectionOptions = ArrayList<SelectionOption>()
        selectionOptions.add(mock(SelectionOption::class.java))
        selectionOptions.add(mock(SelectionOption::class.java))
        selectionOptions.add(mock(SelectionOption::class.java))

        adapter.setSelectionOptions(selectionOptions)

        assertThat(adapter.getItem(0)).isEqualTo(selectionOptions[0])
        assertThat(adapter.getItem(1)).isEqualTo(selectionOptions[1])
        assertThat(adapter.getItem(2)).isEqualTo(selectionOptions[2])
    }

    @Test
    fun getItemId_shouldReturnPositions() {
        val selectionOptions = asList(
                mock(SelectionOption::class.java),
                mock(SelectionOption::class.java),
                mock(SelectionOption::class.java)
        )

        adapter.setSelectionOptions(selectionOptions)

        assertThat(adapter.getItemId(0)).isEqualTo(0)
        assertThat(adapter.getItemId(1)).isEqualTo(1)
        assertThat(adapter.getItemId(2)).isEqualTo(2)
    }

    @SuppressLint("SetTextI18n")
    @Test
    fun getView_shouldBindDataAndReturnViewWithoutConvertView() {
        val selectionOptions = asList(
                mock(SelectionOption::class.java),
                mock(SelectionOption::class.java),
                mock(SelectionOption::class.java)
        )

        adapter.setSelectionOptions(selectionOptions)
        val container = mock(ViewGroup::class.java)

        for (position in selectionOptions.indices) {
            `when`(selectionOptions[position].title()).thenReturn("Title " + position)

            val view = mock(View::class.java)
            `when`(layoutInflater.inflate(R.layout.list_developer_settings_spinner_item, container, false)).thenReturn(view)

            val titleTextView = mock(TextView::class.java)
            `when`<Any>(view.findViewById(R.id.list_developer_settings_spinner_item_title_text_view)).thenReturn(titleTextView)

            // Notice: there is NO convertView, that what we want to check.
            assertThat(adapter.getView(position, null, container)).isSameAs(view)
            verify(titleTextView).text = "Title " + position
        }
    }

    @Test
    fun getView_shouldBindDataAndReturnViewWithConvertView() {
        val selectionOptions = asList(
                mock(SelectionOption::class.java),
                mock(SelectionOption::class.java),
                mock(SelectionOption::class.java)
        )

        adapter.setSelectionOptions(selectionOptions)
        val container = mock(ViewGroup::class.java)

        for (position in selectionOptions.indices) {
            val view = mock(View::class.java)
            `when`(layoutInflater.inflate(R.layout.list_developer_settings_spinner_item, container, false)).thenReturn(view)

            val viewHolder = mock(DeveloperSettingsSpinnerAdapter.ViewHolder::class.java)
            `when`(view.tag).thenReturn(viewHolder)

            // Notice: there IS convertView, that what we want to check.
            assertThat(adapter.getView(position, view, container)).isSameAs(view)
            verify(viewHolder).bindItem(selectionOptions[position])
        }
    }

    @SuppressLint("SetTextI18n")
    @Test
    fun getDropDownView_shouldBindDataAndReturnViewWithoutConvertView() {
        val selectionOptions = asList(
                mock(SelectionOption::class.java),
                mock(SelectionOption::class.java),
                mock(SelectionOption::class.java)
        )

        adapter.setSelectionOptions(selectionOptions)
        val container = mock(ViewGroup::class.java)

        for (position in selectionOptions.indices) {
            `when`(selectionOptions[position].title()).thenReturn("Title " + position)

            val view = mock(View::class.java)
            `when`(layoutInflater.inflate(R.layout.list_developer_settings_spinner_drop_down_item, container, false)).thenReturn(view)

            val titleTextView = mock(TextView::class.java)
            `when`<Any>(view.findViewById(R.id.list_developer_settings_spinner_item_title_text_view)).thenReturn(titleTextView)

            // Notice: there is NO convertView, that what we want to check.
            assertThat(adapter.getDropDownView(position, null, container)).isSameAs(view)
            verify(titleTextView).text = "Title " + position
        }
    }

    @Test
    fun getDropDownView_shouldBindDataAndReturnViewWithConvertView() {
        val selectionOptions = asList(
                mock(SelectionOption::class.java),
                mock(SelectionOption::class.java),
                mock(SelectionOption::class.java)
        )

        adapter.setSelectionOptions(selectionOptions)
        val container = mock(ViewGroup::class.java)

        for (position in selectionOptions.indices) {
            val view = mock(View::class.java)
            `when`(layoutInflater.inflate(R.layout.list_developer_settings_spinner_item, container, false)).thenReturn(view)

            val viewHolder = mock(DeveloperSettingsSpinnerAdapter.ViewHolder::class.java)
            `when`(view.tag).thenReturn(viewHolder)

            // Notice: there IS convertView, that what we want to check.
            assertThat(adapter.getDropDownView(position, view, container)).isSameAs(view)
            verify(viewHolder).bindItem(selectionOptions[position])
        }
    }
}