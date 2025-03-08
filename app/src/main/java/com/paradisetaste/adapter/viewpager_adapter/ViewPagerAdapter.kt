package com.paradisetaste.adapter.viewpager_adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.paradisetaste.fragments.categories_fragments.BeefCategoryFragment
import com.paradisetaste.fragments.categories_fragments.ChikenCategoryFragment
import com.paradisetaste.fragments.categories_fragments.MottonCategoryFragment

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle:Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 14
    }

    override fun createFragment(position: Int): Fragment {
        when{
            position == 0 -> return BeefCategoryFragment()
            position == 1 -> return ChikenCategoryFragment()
            position == 2 -> return MottonCategoryFragment()
            position == 3 -> return BeefCategoryFragment()
            position == 4 -> return ChikenCategoryFragment()
            position == 5 -> return MottonCategoryFragment()
            position == 6 -> return BeefCategoryFragment()
            position == 7 -> return ChikenCategoryFragment()
            position == 8 -> return MottonCategoryFragment()
            position == 9 -> return BeefCategoryFragment()
            position == 10 -> return ChikenCategoryFragment()
            position == 11 -> return MottonCategoryFragment()
            position == 12 -> return BeefCategoryFragment ()
            position == 13 -> return ChikenCategoryFragment()
            else->{
                return BeefCategoryFragment()
            }
        }
    }
}