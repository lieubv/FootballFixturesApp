package com.example.competitiondetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.common.base.BaseFragment
import com.example.competitiondetails.R
import com.example.competitiondetails.databinding.FragmentViewPagerBinding
import com.example.competitiondetails.ui.fixturesFragment.FixturesFragment
import com.example.competitiondetails.ui.tableFragment.TableFragment
import com.example.competitiondetails.ui.teamFragment.TeamFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : BaseFragment() {

    private lateinit var binding: FragmentViewPagerBinding
    private var competitionId: Long = 0L
    private lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_view_pager, container, false)
        val view = binding.root

        getIntents()
        initAdapter()

        // Set the actionbar title
        baseActivity.supportActionBar?.title = name

        return view
    }

    private fun initAdapter(){
        /**
         * Create the adapter that will return a fragment for each of the three primary sections of
         * the viewpager fragment.
         *
         * Using [supportFragmentManager] makes the viewpager fragment contents overlap one another.
         * Thereby, causing irregularities on the fragment layout.
         *
         * To solve the issue above, [childFragmentManager] is used since for placing and managing
         * Fragments inside of this Fragment.
         */
        val viewPageAdapter = ViewPageAdapter(
            childFragmentManager
        )
        viewPageAdapter.addFragment(TableFragment.newInstance().apply {
            arguments = Bundle().apply {
                putLong("id", competitionId)
            }
        }, "Table")
        viewPageAdapter.addFragment(FixturesFragment.newInstance().apply {
            arguments = Bundle().apply {
                putLong("id", competitionId)
            }
        }, "Fixtures")
        viewPageAdapter.addFragment(TeamFragment.newInstance().apply {
            arguments = Bundle().apply {
                putLong("id", competitionId)
            }
        }, "Team")

        // Set up the ViewPager with the sections adapter.
        binding.container.adapter = viewPageAdapter
        binding.tabs.setupWithViewPager(binding.container)
    }

    private fun getIntents() {
        val args: ViewPagerFragmentArgs by navArgs()
        competitionId = args.competition.id
        name = args.competition.name
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    companion object {
        fun newInstance() = ViewPagerFragment()
    }
}
