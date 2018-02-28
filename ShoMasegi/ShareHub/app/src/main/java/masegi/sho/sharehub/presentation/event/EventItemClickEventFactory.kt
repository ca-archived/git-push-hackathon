package masegi.sho.sharehub.presentation.event

import masegi.sho.sharehub.data.model.event.Event
import masegi.sho.sharehub.data.model.event.Event.EventType.*
import masegi.sho.sharehub.presentation.NavigationController

/**
 * Created by masegi on 2018/02/28.
 */

class EventItemClickEventFactory(
        navigationController: NavigationController
)
{


    // MARK: - Property

    private var base: (event: Event?) -> Unit = { event ->

        var htmlUrl: String? = "https://github.com/"
        if (event != null) {

            if (event.repo?.html_url != null) {

                htmlUrl = event.repo.html_url
            }
            else {

                event.repo?.let {  htmlUrl += event.repo.name }
            }
            when(event.type) {

                COMMIT_COMMENT, ISSUE_COMMENT, PULL_REQUEST_REVIEW_COMMENT -> {

                    event.payload.comment?.let { htmlUrl = it.html_url }
                }

                CREATE, DELETE, DEPLOYMENT, LABEL, PAGE_BUILD, PROJECT_CARD,
                PROJECT_COLUMN, PROJECT, PUBLIC_EVENT, REPOSITORY, WATCH -> {

                    event.payload.repository?.let { htmlUrl = it.html_url }
                }

                FOLLOW -> {

                    event.payload.target?.let { htmlUrl = it.htmlUrl }
                }

                FORK -> {

                    event.payload.forkee?.let { htmlUrl = it.html_url }
                }

                GIST -> {

                    event.payload.gist?.let { htmlUrl = it.html_url }
                }

                GOLLUM -> {

                    event.payload.pages?.let { pages ->

                        if (pages.isNotEmpty()) { htmlUrl = pages[0].html_url }
                        else {

                            event.repo?.let { htmlUrl = it.html_url }
                        }
                    }
                }

                ISSUES -> {

                    event.payload.issue?.let { htmlUrl = it.html_url }
                }

                MILESTONE -> {

                    event.payload.milestone?.let { htmlUrl = it.html_url }
                }

                PULL_REQUEST -> {

                    event.payload.pull_request?.let { htmlUrl = it.html_url }
                }

                PULL_REQUEST_REVIEW -> {

                    event.payload.review?.let { htmlUrl = it.html_url }
                }

                PUSH -> {

                    event.payload.compare?.let { htmlUrl = it }
                }

                RELEASE -> {

                    event.payload.release?.let { htmlUrl = it.html_url}
                }

                STATUS -> {

                    event.payload.commit?.let { commit ->

                        commit.html_url?.let { htmlUrl = it }
                    }
                }

                else -> {

                    event.repo?.let { htmlUrl = it.html_url }
                }
            }
            htmlUrl?.let {

                navigationController.navigationToExternalBrowser(it)
            }
        }
    }


    // MARK: - Internal

    fun build(): (event: Event?) -> Unit {

        return base
    }
}