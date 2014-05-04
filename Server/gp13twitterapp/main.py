#!/usr/bin/env python
#
# Copyright 2007 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
import webapp2
from twython import *
import json
TWITTER_APP_KEY = '' #supply the appropriate value
TWITTER_APP_KEY_SECRET = '' 
TWITTER_ACCESS_TOKEN = ''
TWITTER_ACCESS_TOKEN_SECRET = ''

class MainHandler(webapp2.RequestHandler):
    def get(self):
        self.response.write('Hello world!')

class GetTweets(webapp2.RequestHandler):
	def get(self, hashtag, no):
		no = int(no)
		t = Twython(app_key=TWITTER_APP_KEY, 
		            app_secret=TWITTER_APP_KEY_SECRET, 
		            oauth_token=TWITTER_ACCESS_TOKEN, 
		            oauth_token_secret=TWITTER_ACCESS_TOKEN_SECRET)

		search = t.search(q=hashtag,count=no)
		tweets = search['statuses']
		responses = []
		for t in tweets:
			resptweet = {"text":t['text'], "handle":t['user']['screen_name']}
			responses.append(resptweet)
		self.response.write(json.dumps(responses))

app = webapp2.WSGIApplication([
    ('/', MainHandler),
    ('/tweets/(.*)/(.*)', GetTweets)
], debug=True)
